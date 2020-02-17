package topcoder.srm691;
import java.util.*;

public class Moneymanager {
	Random r = new Random(566);
	int n, x;
	Project[] array;

    public int getbest(int[] a, int[] b, int X) {
    	x = X;
    	n = a.length;
    	array = new Project[n];
    	for (int i = 0; i < n; i++) {
    		array[i] = new Project(a[i], b[i]);
    	}
    	Arrays.sort(array, new Comparator<Project>() {
    		@Override
    		public int compare(Project p, Project q) {
    			return Integer.compare(q.a * p.b, p.a * q.b);
    		}
		});
    	Settings settings = new Settings();
    	Annealable answer = simulatedAnnealing(new Instance(r), settings, r);
    	return -answer.energy();
    }

	class Project {
		int a, b;

		public Project(int a, int b) {
			this.a = a;
			this.b = b;
		}
	}

    class Instance implements AnnealableWithStepBack {
    	long first;
    	int swapA, swapB;

    	public Instance(Random random) {
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < n; i++) {
				list.add(i % 2);
			}
			Collections.shuffle(list, random);
			for (int i = 0; i < n; i++) {
				first |= list.get(i) * 1L << i;
			}
		}

		@Override
		public int energy() {
			int res = 0;
			int exp = 0;
			for (int t = 0; t < 2; t++) {
				for (int i = 0; i < n; i++) {
					if (((first >> i) & 1) != t) {
						continue;
					}
					exp += array[i].a;
					res += array[i].b * exp;
				}
				exp += x;
			}
			return -res;
		}

		@Override
		public void vary(Random random) {
			for (;;) {
				swapA = random.nextInt(n);
				swapB = random.nextInt(n);
				if (((first >> swapA) & 1) == ((first >> swapB) & 1)) {
					continue;
				}
				undo();
				return;
			}
		}

		@Override
		public void undo() {
			first ^= (1L << swapA) ^ (1L << swapB);
		}

		@Override
		public AnnealableWithStepBack clone() {
			Instance clone;
			try {
				clone = (Instance) super.clone();
			} catch (CloneNotSupportedException e) {
				throw new RuntimeException(e);
			}
			clone.first = first;
			return clone;
		}

		@Override
		public Annealable randomInstance(Random random) {
			return new Instance(random);
		}
    }

	public static interface Annealable {
		public int energy();
		public Annealable randomInstance(Random random);
	}

	public static interface AnnealableWithoutStepBack extends Annealable {
		public AnnealableWithoutStepBack vary(Random random);
	}

	public static interface AnnealableWithStepBack extends Annealable, Cloneable {
		public void vary(Random random);
		public void undo();
		public AnnealableWithStepBack clone();
	}

	public static class Settings {
		public int globalIterations;
		public int iterations;
		public double probStartWithPrevious;
		public int recessionLimit;
		public double desiredEnergy;
		public double temp0;

		public Settings(int globalIterations, int iterations, double probStartWithPrevious, int recessionLimit, double desiredEnergy, double temp0) {
			this.globalIterations = globalIterations;
			this.iterations = iterations;
			this.probStartWithPrevious = probStartWithPrevious;
			this.recessionLimit = recessionLimit;
			this.desiredEnergy = desiredEnergy;
			this.temp0 = temp0;
		}

		public Settings() {
			this(256, 1024, 1 - 1.0 / 16, Integer.MAX_VALUE, -Double.MAX_VALUE, 1);
		}
	}

	public static Annealable simulatedAnnealing(Annealable item, Settings settings, Random r) {
		boolean stepBack = item instanceof AnnealableWithStepBack;
		Annealable answer = null;
		double answerEnergy = Double.MAX_VALUE;
		double energy = item.energy();
		for (int glob = 0; glob < settings.globalIterations; glob++) {
			if (glob > 0 && r.nextDouble() >= settings.probStartWithPrevious) {
				item = item.randomInstance(r);
				energy = item.energy();
			}
			for (int iter = 1, recession = 0; iter <= settings.iterations; iter++) {
				double nextEnergy;
				AnnealableWithoutStepBack next = null;
				if (stepBack) {
					((AnnealableWithStepBack) item).vary(r);
					nextEnergy = item.energy();
				} else {
					next = ((AnnealableWithoutStepBack) item).vary(r);
					nextEnergy = next.energy();
				}
				double dEnergy = energy - nextEnergy;
				boolean accept;
				if (dEnergy >= 0) {
					accept = true;
					recession = 0;
				} else {
					recession++;
					if (recession == settings.recessionLimit) {
						break;
					}
					double barrier = Math.exp(dEnergy * iter / settings.temp0);
					accept = r.nextDouble() < barrier;
				}
				if (accept) {
					if (!stepBack) {
						assert(next != null);
						item = next;
					}
					energy = nextEnergy;
					if (energy < answerEnergy) {
						answerEnergy = energy;
						if (stepBack) {
							answer = ((AnnealableWithStepBack) item).clone();
						} else {
							answer = item;
						}
						if (answerEnergy <= settings.desiredEnergy) {
							return answer;
						}
					}
				} else {
					if (stepBack) {
						((AnnealableWithStepBack) item).undo();
					}
				}
			}
		}
		return answer;
	}


}

package marathons.yandex.y2018.round1;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class Taxis {
	static final boolean SUBMIT = false;
	static final boolean VISUALIZE = false;//!SUBMIT;
	static final int SEED_FROM = 1;
	static final int SEED_COUNT = 10;
	static final int MAX_TAXIS = 20;
	static final int MAX_ZONES = 20;
	static final int MAX_PEOPLE = 500;
	static final int MAX_COORD = SUBMIT ? 10_000 : 1000;

	int taxis, people, zones, coord;
	int[] xt, yt, xp, yp, xz, yz;
	boolean[] alive;
	int[] closestZoneToPerson;
	double score;
	ArrayList<String> instructions;
	double theScore = Double.MAX_VALUE;
	ArrayList<String> theInstructions;

	void solve() {
		int[] xtSave = xt.clone();
		int[] ytSave = yt.clone();
		int taxisSave = taxis;
		if (!SUBMIT) {
			System.out.print(taxis + "\t" + people + "\t" + zones + "\t");
		}
		{
			int tTreshold = Math.min(taxisSave, 3);
//		for (int tTreshold = 1; tTreshold <= Math.min(taxisSave, 4); tTreshold++) {
			instructions = new ArrayList<>();
			score = 0;
			xt = xtSave.clone();
			yt = ytSave.clone();
			alive = new boolean[people];
			Arrays.fill(alive, true);
			taxis = taxisSave;
			try {
				solveA(tTreshold);
				relax();
			} catch (Exception e) {
			}
		}
		out.println(theInstructions.size());
		for (String s : theInstructions) {
			out.println(s);
		}
		if (!SUBMIT) {
			System.out.println(theScore);
		}
	}

	void relax() {
		if (score < theScore) {
			theScore = score;
			theInstructions = new ArrayList<>(instructions);
		}
	}

	void solveA(int tTreshold) {
		vis();
		ArrayList<Integer> zoneIndices = new ArrayList<>();
		for (int i = 0; i < zones; i++) {
			zoneIndices.add(i);
		}
		for (int i = 0; i < taxis; i++) {
			int j = closestLivePerson(xt[i], yt[i]);
			if (j == -1) {
				break;
			}
			alive[j] = false;
			moveOneTo(i, xp[j], yp[j]);
		}
		Collections.sort(zoneIndices, new Comparator<Integer>() {
			@Override
			public int compare(Integer p, Integer q) {
				return xz[p] + yz[p] - xz[q] - yz[q];
			}
		});
		for (int zIter = 0; zIter < zones; zIter++) {
			int z = zoneIndices.get(zIter);
			if (zIter == 0) {
				for (int i = 0; i < taxis; i++) {
					moveOneTo(i, xz[z] - taxis + 1 + i, yz[z]);
				}
			} else {
				moveAll(xz[z] - xt[0] - taxis + 1, yz[z] - yt[0]);
			}
			for (int i = 0; i < taxis - 1; i++) {
				moveAll(1, 0);
			}
			for (int i = 0; i < taxis - 1; i++) {
				moveAll(-1, 0);
			}
			for (int i = 0; i < taxis - 1; i++) {
				moveAll(1, 0);
			}
			if (zIter == 0) {
				moveMany(coord + taxis - xt[taxis - 1], coord + taxis - yt[taxis - 1], tTreshold, taxis);
				taxis = Math.min(taxis, tTreshold);
			}
			for (int i = 0; i < people; i++) {
				if (yp[i] == yz[z] && Math.abs(xp[i] - xz[z]) <= taxis - 1) {
					alive[i] = false;
				}
			}

			for (;;) {
				ArrayList<Integer> arriveHere = new ArrayList<>();
				for (int i = 0; i < people; i++) {
					if (!alive[i]) {
						continue;
					}
					if (closestZoneToPerson[i] == z) {
						arriveHere.add(i);
					}
				}
				if (arriveHere.isEmpty()) {
					break;
				}
				int orderSize = Math.min(arriveHere.size(), taxis);
				ArrayList<Integer> list = new ArrayList<>(arriveHere);
				int bestI = -1;
				int bestCluster = 4 * coord;
				for (int i : arriveHere) {
					final int iCopy = i;
					Collections.sort(list, new Comparator<Integer>() {
						@Override
						public int compare(Integer p, Integer q) {
							return Math.max(Math.abs(xp[p] - xp[iCopy]), Math.abs(yp[p] - yp[iCopy])) -
									Math.max(Math.abs(xp[q] - xp[iCopy]), Math.abs(yp[q] - yp[iCopy]));
						}
					});
					int p = list.get(orderSize - 1);
					int cluster = Math.max(Math.abs(xp[p] - xp[i]), Math.abs(yp[p] - yp[i]));
					if (cluster < bestCluster) {
						bestCluster = cluster;
						bestI = i;
					}
				}
				int x1 = xp[bestI] - bestCluster;
				int y1 = yp[bestI] - bestCluster;
				x1 = Math.max(x1, -coord);
				y1 = Math.max(y1, -coord);
				mission(x1, y1, x1 + 2 * bestCluster, y1 + 2 * bestCluster);
			}
		}
	}

	void mission(int x1, int y1, int x2, int y2) {
		int xBase = xt[0];
		int yBase = yt[0];
		int fleet = taxis;
		moveMany(x1 - xBase, y1 - yBase, 0, fleet);
		boolean[] busy = new boolean[fleet];
		ArrayList<Integer> potential = new ArrayList<>();
		int instant = 0;
		for (int i = 0; i < people; i++) {
			if (!alive[i]) {
				continue;
			}
			if (yp[i] == y1 && xp[i] >= x1 && xp[i] < x1 + fleet) {
				alive[i] = false;
				busy[xp[i] - x1] = true;
				instant++;
				continue;
			}
			if (yp[i] >= y1 && yp[i] <= y2 && xp[i] >= x1 && xp[i] <= x2) {
				potential.add(i);
			}
		}
		Collections.sort(potential, new Comparator<Integer>() {
			@Override
			public int compare(Integer p, Integer q) {
				if (yp[p] != yp[q]) {
					return yp[p] - yp[q];
				}
				return xp[p] - xp[q];
			}
		});
		int nonInstant = Math.min(fleet - instant, potential.size());
		potential = new ArrayList<Integer>(potential.subList(0, nonInstant));
//		y2 = yp[potential.get(potential.size() - 1)];
		Collections.sort(potential, new Comparator<Integer>() {
			@Override
			public int compare(Integer p, Integer q) {
				if (xp[p] != xp[q]) {
					return xp[p] - xp[q];
				}
				return yp[p] - yp[q];
			}
		});
		for (int i = 0, k = 0; i < fleet; i++) {
			if (busy[i]) {
				continue;
			}
			if (k < nonInstant) {
				int p = potential.get(k++);
				moveOneTo(i, xp[p], yp[p]);
				alive[p] = false;
				busy[i] = true;
				moveOneTo(i, x1 + i, y1);
			}
		}
		moveMany(xBase - (fleet - 1) - x1, yBase - y1, 0, fleet);
		for (int i = 0; i < fleet - 1; i++) {
			moveMany(1, 0, 0, fleet);
		}
	}

	int closestLivePerson(int x, int y) {
		double bestDist = 4 * coord;
		int bestI = -1;
		for (int i = 0; i < people; i++) {
			if (!alive[i]) {
				 continue;
			}
			double dist = distPerson(i, x, y);
			if (dist < bestDist) {
				bestDist = dist;
				bestI = i;
			}
		}
		return bestI;
	}

	int closestZone(int x, int y) {
		double bestDist = 4 * coord;
		int bestI = -1;
		for (int i = 0; i < zones; i++) {
			double dist = distZone(i, x, y);
			if (dist < bestDist) {
				bestDist = dist;
				bestI = i;
			}
		}
		return bestI;
	}

	double distPerson(int i, int x, int y) {
		int dx = xp[i] - x;
		int dy = yp[i] - y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	double distZone(int i, int x, int y) {
		int dx = xz[i] - x;
		int dy = yz[i] - y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	void moveOneTo(int i, int xTo, int yTo) {
		move(xTo - xt[i], yTo - yt[i], i);
	}

	int[] allIndices;

	void moveAll(int dx, int dy) {
		move(dx, dy, allIndices);
	}

	void moveMany(int dx, int dy, int from, int to) {
		int[] indices = new int[to - from];
		for (int i = from; i < to; i++) {
			indices[i - from] = i;
		}
		move(dx, dy, indices);
	}

	void move(int dx, int dy, int... indices) {
		if (dx == 0 && dy == 0 || indices.length == 0) {
			return;
		}
		String instruction = "MOVE " + dx + " " + dy + " " + indices.length;
		for (int i : indices) {
			instruction += " " + (i + 1);
			xt[i] += dx;
			yt[i] += dy;
		}
		for (int i : indices) {
			for (int j = 0; j < i; j++) {
				if (xt[i] == xt[j] && yt[i] == yt[j]) {
					throw new RuntimeException("Accident");
				}
			}
		}
		instructions.add(instruction);
		score += Math.sqrt(dx * dx + dy * dy) * (1 + indices.length / taxis);
		vis(dx, dy, indices);
	}

	void init() {
		for (int i = 0; i < people; i++) {
			coord = Math.max(coord, Math.abs(xp[i]));
			coord = Math.max(coord, Math.abs(yp[i]));
		}
		allIndices = new int[taxis];
		for (int i = 0; i < taxis; i++) {
			allIndices[i] = i;
		}
		closestZoneToPerson = new int[people];
		for (int i = 0; i < people; i++) {
			closestZoneToPerson[i] = closestZone(xp[i], yp[i]);
		}
	}

	void vis() {
		vis(0, 0);
	}

	void vis(int dx, int dy, int... indices) {
		if (!VISUALIZE) {
			return;
		}
		int sz = 500;
		double scale = 0.9;
		int p = 3;
		BufferedImage image = new BufferedImage(sz, sz, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, sz, sz);
		g.setColor(Color.ORANGE);
		for (int i = 0; i < zones; i++) {
			int xs = (int) ((xz[i] * 1.0 / coord * scale + 1) * (sz / 2));
			int ys = (int) ((yz[i] * 1.0 / coord * scale + 1) * (sz / 2));
			g.fillOval(xs - 2 * p, ys - 2 * p, 4 * p + 1, 4 * p + 1);
		}
		g.setColor(Color.BLUE);
		for (int i = 0; i < people; i++) {
			if (!alive[i]) {
				continue;
			}
			int xs = (int) ((xp[i] * 1.0 / coord * scale + 1) * (sz / 2));
			int ys = (int) ((yp[i] * 1.0 / coord * scale + 1) * (sz / 2));
			g.fillOval(xs - p, ys - p, 2 * p + 1, 2 * p + 1);
		}
		g.setColor(Color.RED);
		for (int i = 0; i < taxis; i++) {
			int xs = (int) ((xt[i] * 1.0 / coord * scale + 1) * (sz / 2));
			int ys = (int) ((yt[i] * 1.0 / coord * scale + 1) * (sz / 2));
			g.fillOval(xs - p, ys - p, 2 * p + 1, 2 * p + 1);
		}
		for (int i : indices) {
			int x1 = (int) ((xt[i] * 1.0 / coord * scale + 1) * (sz / 2));
			int y1 = (int) ((yt[i] * 1.0 / coord * scale + 1) * (sz / 2));
			int x2 = (int) (((xt[i] - dx) * 1.0 / coord * scale + 1) * (sz / 2));
			int y2 = (int) (((yt[i] - dy) * 1.0 / coord * scale + 1) * (sz / 2));
			g.drawLine(x1, y1, x2, y2);
		}
		g.setColor(Color.BLACK);
		g.drawString("score: " + Math.round(score), 15, 15);
		imageSaver.write(image);
	}

	public Taxis(Scanner in) {
		taxis = in.nextInt();
		xt = new int[taxis];
		yt = new int[taxis];
		for (int i = 0; i < taxis; i++) {
			xt[i] = in.nextInt();
			yt[i] = in.nextInt();
		}
		people = in.nextInt();
		xp = new int[people];
		yp = new int[people];
		for (int i = 0; i < people; i++) {
			xp[i] = in.nextInt();
			yp[i] = in.nextInt();
		}
		zones = in.nextInt();
		xz = new int[zones];
		yz = new int[zones];
		for (int i = 0; i < zones; i++) {
			xz[i] = in.nextInt();
			yz[i] = in.nextInt();
		}
		init();
	}

	public Taxis(Random random) {
		int s;
		do {
			taxis = random.nextInt(MAX_TAXIS) + 1;
			people = random.nextInt(MAX_PEOPLE) + 1;
			zones = random.nextInt(MAX_ZONES) + 1;
			s = random.nextInt(MAX_COORD) + 1;
		} while (taxis + people + zones > (2 * s + 1) * (2 * s + 1));
		ArrayList<Point> list = new ArrayList<>();
		for (int i = -s; i <= s; i++) {
			for (int j = -s; j <= s; j++) {
				list.add(new Point(i, j));
			}
		}
		Collections.shuffle(list, random);
		int k = 0;
		xt = new int[taxis];
		yt = new int[taxis];
		for (int i = 0; i < taxis; i++) {
			xt[i] = list.get(k).x;
			yt[i] = list.get(k).y;
			k++;
		}
		xp = new int[people];
		yp = new int[people];
		for (int i = 0; i < people; i++) {
			xp[i] = list.get(k).x;
			yp[i] = list.get(k).y;
			k++;
		}
		xz = new int[zones];
		yz = new int[zones];
		for (int i = 0; i < zones; i++) {
			xz[i] = list.get(k).x;
			yz[i] = list.get(k).y;
			k++;
		}
		init();
	}

	static ImageSaver imageSaver = new ImageSaver();

	static class ImageSaver {
		String format = "png";
		String htmlFileName = "pics~.html";
		PrintWriter html;
		String prefix = "";
		int imageCount;
		int totalCount;

		public ImageSaver() {
			try {
				html = new PrintWriter(htmlFileName);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
			html.println("<html><body><center>");
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
			imageCount = 0;
		}

		public void write(BufferedImage image) {
			try {
				String filename = prefix + "_" + (imageCount) + "~." + format;
				ImageIO.write(image, format, new File(filename));
				html.println("<a href='#mark" + (totalCount - 1) + "'>prev</a>");
				html.println("<a name='mark" + totalCount + "'>" + filename + "</a>");
				html.println("<a href='#mark" + (totalCount + 1) + "'>next</a>");
				html.println("<br/><img src='" + filename + "'/><br/><br/>");
				html.flush();
				imageCount++;
				totalCount++;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	static PrintWriter out;

	public static void main(String[] args) throws IOException {
		String inputFileName = "input.txt";
		String outputFileName = "output.txt";

		Scanner in = new Scanner(new File(inputFileName));
		out = new PrintWriter(outputFileName);
		if (!SUBMIT) {
			for (int seed = SEED_FROM; seed < SEED_FROM + SEED_COUNT; seed++) {
				imageSaver.setPrefix("" + seed);
				new Taxis(new Random(seed)).solve();;
			}
			imageSaver.setPrefix("input");
		}
		new Taxis(in).solve();
		in.close();
		out.close();
	}
}

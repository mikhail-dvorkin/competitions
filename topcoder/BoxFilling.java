package topcoder;
public class BoxFilling {
	public long getNumber(int sizeX, int sizeY, int sizeZ, int cubeX, int cubeY, int cubeZ) {
		return 1 + get(sizeX, sizeY, sizeZ, cubeX, cubeY, cubeZ);
	}

	private long get(long sx, long sy, long sz, long x, long y, long z) {
		long a = Math.min(Math.min(x, y), z);
		long s = Math.min(Math.min(sx, sy), sz);
		long cut = Math.min(a - 1, s - 2);
		if (cut > 0) {
			long v = sx * sy * sz;
			long vrest = (sx - cut) * (sy - cut) * (sz - cut);
			return get(sx - cut, sy - cut, sz - cut, x - cut, y - cut, z - cut) + v - vrest;
		}
		long cum = 0;
		if (s >= 2) {
			if (z == 1) {
				return get(sx, sy, 1, x, y, z);
			}
			cum += sx * sy;
			sz--;
			z--;
			if (sz > 1) {
				if (y == 1) {
					return cum + get(sx, 1, sz, x, y, z);
				}
				cum += sx * sz;
				sy--;
				y--;
				if (sy > 1) {
					if (x == 1) {
						return cum + get(1, sy, sz, x, y, z);
					}
					cum += sy * sz;
					sx--;
					x--;
				}
			}
			return cum + get(sx, sy, sz, x, y, z);
		}
		int dim = dim(sx, sy, sz);
		if (dim == 1) {
			cut = sx + sy + sz;
			if (sx > 1) {
				cut = Math.min(cut, x - 1);
				cut = Math.min(cut, sx - 2);
			}
			if (sy > 1) {
				cut = Math.min(cut, y - 1);
				cut = Math.min(cut, sy - 2);
			}
			if (sz > 1) {
				cut = Math.min(cut, z - 1);
				cut = Math.min(cut, sz - 2);
			}
			if (cut > 0) {
				long v = sx * sy * sz;
				long vrest = Math.max(sx - cut, 1) * Math.max(sy - cut, 1) * Math.max(sz - cut, 1);
				return get(Math.max(sx - cut, 1), Math.max(sy - cut, 1), Math.max(sz - cut, 1), Math.max(x - cut, 1), Math.max(y - cut, 1), Math.max(z - cut, 1)) + v - vrest;
			}
			if (sx > 1) {
				if (y == 1 && z == 1) {
					return get(sx, 1, 1, x, y, z);
				}
				if (sy > 1) {
					sy--;
					y--;
				} else {
					sz--;
					z--;
				}
				cum += sx;
			}
			if (dim(sx, sy, sz) == 1) {
				if (sy > 1) {
					if (x == 1 && z == 1) {
						return cum + get(1, sy, 1, x, y, z);
					}
					if (sx > 1) {
						sx--;
						x--;
					} else {
						sz--;
						z--;
					}
					cum += sy;
				}
			}
			if (dim(sx, sy, sz) == 1) {
				if (sz > 1) {
					if (x == 1 && y == 1) {
						return cum + get(1, 1, sz, x, y, z);
					}
					if (sx > 1) {
						sx--;
						x--;
					} else {
						sy--;
						y--;
					}
					cum += sz;
				}
			}
			return cum + get(sx, sy, sz, x, y, z);
		}
		return x + y + z - 3;
	}

	private int dim(long sx, long sy, long sz) {
		int dim = 0;
		if (sx == 1)
			dim++;
		if (sy == 1)
			dim++;
		if (sz == 1)
			dim++;
		return dim;
	}
	
	public static void main(String[] args) {
		long ans = new BoxFilling().getNumber(8, 325252673, 325252673, 8, 325252673, 325252673);
		System.out.println(ans);
//		int sx = 2;
//		int sy = 14;
//		int sz = 13;
//		for (int z = 1; z <= sz; z++) {
//			for (int y = 1; y <= sy; y++) {
//				for (int x = 1; x <= sx; x++) {
//					System.out.print(new BoxFilling().getNumber(sx, sy, sz, x, y, z) + " ");
//				}
//				System.out.println();
//			}
//			System.out.println();
//		}
	}
}

package class01;

public class Test {

	public static int ways1(int x, int y, int k) {
		return f(x, y, k);
	}

	// 马从(0,0)出发，有K步要走，并且一定要走完，最终来到x，y位置的方法数是多少
	public static int f(int x, int y, int k) {
		if (k == 0) {
			return x == 0 && y == 0 ? 1 : 0;
		}
		if (x < 0 || x > 9 || y < 0 || y > 8) {
			return 0;
		}
		// 有步数要走, x，y 也是棋盘上的位置
		return f(x + 2, y - 1, k - 1) + f(x + 2, y + 1, k - 1) + f(x + 1, y + 2, k - 1) + f(x - 1, y + 2, k - 1)
				+ f(x - 2, y + 1, k - 1) + f(x - 2, y - 1, k - 1) + f(x - 1, y - 2, k - 1) + f(x + 1, y - 2, k - 1);
	}

	public static int ways2(int x, int y, int k) {
		int[][][] dp = new int[10][9][k + 1];// 0~k

		dp[0][0][0] = 1; // dp[..][..][0] = 0

		for (int level = 1; level <= k; level++) {
			// level层，x y
			for (int i = 0; i < 10; i++) { // x可能性
				for (int j = 0; j < 9; j++) { // y的可能性
					dp[i][j][level] = getValue(dp, i + 2, j - 1, level - 1) + getValue(dp, i + 2, j + 1, level - 1)
							+ getValue(dp, i + 1, j + 2, level - 1) + getValue(dp, i - 1, j + 2, level - 1)
							+ getValue(dp, i - 2, j + 1, level - 1) + getValue(dp, i - 2, j - 1, level - 1)
							+ getValue(dp, i - 1, j - 2, level - 1) + getValue(dp, i + 1, j - 2, level - 1);
				}
			}
		}

		return dp[x][y][k];
	}

	public static int getValue(int[][][] dp, int x, int y, int k) {
		if (x < 0 || x > 9 || y < 0 || y > 8) {
			return 0;
		}
		return dp[x][y][k];
	}
	
	
	public static int ways3(int x, int y, int k) {
		return p(0,0,k,x,y);
	}
	
	// 当前来到row，col位置，还剩rest步，走完rest步之后，来到x，y位置，方法数多少
	public static int p(int row, int col, int rest, int x, int y) {
		if(rest == 0) {
			return row == x && col == y ? 1 :0;
		}
		
		if (row < 0 || row > 9 || col < 0 || col > 8) {
			return 0;
		}
		
		return p(row + 2, col - 1, rest - 1, x, y) 
				+ p(row + 2, col + 1, rest - 1, x, y) 
				+ p(row + 1, col + 2, rest - 1, x, y) 
				+ p(row - 1, col + 2, rest - 1, x, y)
				+ p(row - 2, col + 1, rest - 1, x, y) 
				+ p(row - 2, col - 1, rest - 1, x, y) 
				+ p(row - 1, col - 2, rest - 1, x, y) 
				+ p(row + 1, col - 2, rest - 1, x, y);
		
	}
	
	
	
	
	
	

	public static void main(String[] args) {
		int x = 6;
		int y = 8;
		int k = 10;
		System.out.println(ways1(x, y, k));
		System.out.println(ways2(x, y, k));
		System.out.println(ways3(x, y, k));

	}

}

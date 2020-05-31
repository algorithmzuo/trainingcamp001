package class07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Code04_CoverMax {

	public static class Rectangle {
		public int up;
		public int down;
		public int left;
		public int right;

		public Rectangle(int up, int down, int left, int right) {
			this.up = up;
			this.down = down;
			this.left = left;
			this.right = right;
		}

	}

	public static class DownComparator implements Comparator<Rectangle> {
		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			return o1.down - o2.down;
		}
	}

	public static class LeftComparator implements Comparator<Rectangle> {
		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			return o1.left - o2.left;
		}
	}

	public static class RightComparator implements Comparator<Rectangle> {
		@Override
		public int compare(Rectangle o1, Rectangle o2) {
			return o1.right - o2.right;
		}
	}

	// 矩形数量是N
	// O(N*LogN)
	// +
	// O(N) * [ O(N) +   O(N *LogN)  ]
	public static int maxCover(Rectangle[] recs) {
		if (recs == null || recs.length == 0) {
			return 0;
		}
		// 根据down（底）排序
		Arrays.sort(recs, new DownComparator());
		// 可能会对当前底边的公共局域，产生影响的矩形
		// list ->  treeSet(有序表表达)
		TreeSet<Rectangle> leftOrdered = new TreeSet<>(new LeftComparator());
		int ans = 0;
		// O(N)
		for (int i = 0; i < recs.length; i++) { // 依次考察每一个矩形的底边
			int curDown = recs[i].down; // 当前的底边值取出来
			int index = i;
			while (recs[index].down == curDown) {
				leftOrdered.add(recs[index]); // O(logN)
				index++;
			}
			i = index;
			// O(N) list是不是有一些顶<=底的矩形
			removeLowerOnCurDown(leftOrdered, curDown);
			// 维持了右边界排序的容器
			TreeSet<Rectangle> rightOrdered = new TreeSet<>(new RightComparator());
			for (Rectangle rec : leftOrdered) { // O(N)
				removeLeftOnCurLeft(rightOrdered, rec.left);
				rightOrdered.add(rec);// O(logN)
				ans = Math.max(ans, rightOrdered.size());
			}
		}
		return ans;
	}

	public static void removeLowerOnCurDown(TreeSet<Rectangle> set, int curDown) {
		List<Rectangle> removes = new ArrayList<>();
		for (Rectangle rec : set) {
			if (rec.up <= curDown) {
				removes.add(rec);
			}
		}
		for (Rectangle rec : removes) {
			set.remove(rec);
		}
	}

	public static void removeLeftOnCurLeft(TreeSet<Rectangle> rightOrdered, int curLeft) {
		List<Rectangle> removes = new ArrayList<>();
		for (Rectangle rec : rightOrdered) {
			if (rec.right > curLeft) {
				break;
			}
			removes.add(rec);
		}
		for (Rectangle rec : removes) {
			rightOrdered.remove(rec);
		}
	}

}

package com.atguigu.exer;

public class Flow {
	private void fallThroughComments(int i) {
		switch (i) {
		case 1:
			System.out.println(i);
			// fall-through
		case 2:
			// FALL-THROUGH
			System.out.println(i);
		case 3:
			// fallthrough
			System.out.println(i);
		case 4:
			//$FALL-THROUGH$
			System.out.println(i);
		case 5:
			// fallthru
			System.out.println(i);
		case 6:
			// falls-through
			System.out.println(i);
		case 7:
			System.out.println(i); // fall through
		case 8:
			System.out.println("foo");
			break;
		default:
			System.out.println("哈哈");
		}
	}
}

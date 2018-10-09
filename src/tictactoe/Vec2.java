package tictactoe;

public abstract class Vec2<T> {
	public final T x;
	public final T y;

	public Vec2(T x, T y) {
		this.x = x;
		this.y = y;
	}

	public abstract Vec2<T> add(Vec2<T> other);
	public abstract Vec2<T> subtract(Vec2<T> other);
	public abstract Vec2<T> scale(Vec2<T> magnitude);

	public static class Int extends Vec2<Integer> {
		public Int(Integer x, Integer y) {
			super(x, y);
		}

		@Override
		public Vec2<Integer> add(Vec2<Integer> other) {
			return new Int(this.x + other.x, this.y + other.y);
		}

		@Override
		public Vec2<Integer> subtract(Vec2<Integer> other) {
			return new Int(this.x - other.x, this.y - other.y);
		}

		public Vec2<Integer> scale(Vec2<Integer> magnitude) {
			return new Int(this.x * magnitude.x, this.y * magnitude.y);
		}

		@Override
		public boolean equals(Object other) {
			if (!(other instanceof Vec2.Int)) {
				return false;
			}

			Int ipOther = (Int)other;
			return ipOther.x.equals(this.x) && ipOther.y.equals(this.y);
		}
	}
}

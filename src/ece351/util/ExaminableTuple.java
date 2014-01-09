package ece351.util;

import java.util.Collection;

/**
 * Generic pair of objects. Used by methods that need to return two values.
 */
public final class ExaminableTuple<X, Y> implements Examinable {
	public final X x;
	public final Y y;

	public ExaminableTuple(final X x, final Y y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean isomorphic(final Examinable obj) {
		return examine(Examiner.Isomorphic, obj);
	}

	@Override
	public boolean equivalent(final Examinable obj) {
		return examine(Examiner.Equivalent, obj);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Examinable) {
			return examine(Examiner.Equals, (Examinable)obj);
		} else {
			return false;
		}
	}
	
	private boolean examine(final Examiner examiner, final Examinable obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!getClass().equals(obj.getClass())) return false;
		@SuppressWarnings("rawtypes")
		final ExaminableTuple that = (ExaminableTuple) obj;
		// x
		// two cases: objects and collections
		if (this.x instanceof Collection) {
//			final Collection<Examinable> c1 = (Collection<Examinable>) this.x;
//			final Collection<Examinable> c2 = (Collection<Examinable>) that.x;
			throw new UnsupportedOperationException("not yet implemented");
		} else {
			final Examinable e1 = (Examinable) this.x;
			final Examinable e2 = (Examinable) that.x;
			if (!examiner.examine(e1, e2)) return false;
		}
		// y
		if (this.y instanceof Collection) {
			throw new UnsupportedOperationException("not yet implemented");
		} else {
			final Examinable e1 = (Examinable) this.y;
			final Examinable e2 = (Examinable) that.y;
			if (!examiner.examine(e1, e2)) return false;
		}
		// no significant differences
		return true;
	}
}

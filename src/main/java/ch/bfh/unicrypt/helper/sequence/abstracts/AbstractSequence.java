/*
 * UniCrypt
 *
 *  UniCrypt(tm): Cryptographic framework allowing the implementation of cryptographic protocols, e.g. e-voting
 *  Copyright (C) 2015 Bern University of Applied Sciences (BFH), Research Institute for
 *  Security in the Information Society (RISIS), E-Voting Group (EVG)
 *  Quellgasse 21, CH-2501 Biel, Switzerland
 *
 *  Licensed under Dual License consisting of:
 *  1. GNU Affero General Public License (AGPL) v3
 *  and
 *  2. Commercial license
 *
 *
 *  1. This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *  2. Licensees holding valid commercial licenses for UniCrypt may use this file in
 *   accordance with the commercial license agreement provided with the
 *   Software or, alternatively, in accordance with the terms contained in
 *   a written agreement between you and Bern University of Applied Sciences (BFH), Research Institute for
 *   Security in the Information Society (RISIS), E-Voting Group (EVG)
 *   Quellgasse 21, CH-2501 Biel, Switzerland.
 *
 *
 *   For further information contact <e-mail: unicrypt@bfh.ch>
 *
 *
 * Redistributions of files must retain the above copyright notice.
 */
package ch.bfh.unicrypt.helper.sequence.abstracts;

import ch.bfh.unicrypt.UniCrypt;
import ch.bfh.unicrypt.helper.array.classes.DenseArray;
import ch.bfh.unicrypt.helper.array.interfaces.ImmutableArray;
import ch.bfh.unicrypt.helper.math.MathUtil;
import ch.bfh.unicrypt.helper.sequence.interfaces.Mapping;
import ch.bfh.unicrypt.helper.sequence.interfaces.Predicate;
import ch.bfh.unicrypt.helper.sequence.interfaces.Sequence;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;

/**
 * This interface represents the concept of an iterable sequence of values similar to streams in Java 8. No means are
 * provided to directly access or manipulate the values. Computational operations can be described declaratively as a
 * pipeline. The execution of the pipeline is lazy. The length of a sequence is either finite and known, finite but
 * unknown, or infinite.
 * <p>
 * @author R. Haenni
 * @version 2.0
 * @param <V> The generic type of the values contained in the sequence
 */
public abstract class AbstractSequence<V>
	   extends UniCrypt
	   implements Sequence<V> {

	// the length of the sequence
	private BigInteger length;

	@Override
	public final boolean isEmpty() {
		return this.getLength().signum() == 0;
	}

	@Override
	public final BigInteger getLength() {
		if (this.length == null) {
			this.length = this.abstractGetLength();
			if (this.length.equals(Sequence.UNKNOWN) && !this.iterator().hasNext()) {
				this.length = MathUtil.ZERO;
			}
			return this.length;
		}
		return this.length;
	}

	@Override
	public final long count() {
		if (this.getLength().equals(Sequence.INFINITE)) {
			throw new UnsupportedOperationException();
		}
		if (this.getLength().equals(Sequence.UNKNOWN)) {
			long counter = 0;
			for (V value : this) {
				counter++;
			}
			this.length = BigInteger.valueOf(counter);
		}
		return this.length.longValue();
	}

	@Override
	public final long count(Predicate<? super V> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException();
		}
		if (this.getLength().equals(Sequence.INFINITE)) {
			throw new UnsupportedOperationException();
		}
		long counter = 0;
		for (V value : this) {
			if (predicate.test(value)) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public final V find() {
		return this.find(1);
	}

	@Override
	public final V find(long n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
		for (V value : this) {
			n--;
			if (n == 0) {
				return value;
			}
		}
		return null;

	}

	@Override
	public final V find(Predicate<? super V> predicate) {
		return this.find(predicate, 1);
	}

	@Override
	public final V find(Predicate<? super V> predicate, long n) {
		if (predicate == null || n < 1) {
			throw new IllegalArgumentException();
		}
		for (V value : this.filter(predicate)) {
			n--;
			if (n == 0) {
				return value;
			}
		}
		return null;
	}

	@Override
	public final <W> Sequence<W> map(final Mapping<? super V, ? extends W> mapping) {
		if (mapping == null) {
			throw new IllegalArgumentException();
		}
		final Sequence<V> source = this;
		return new AbstractSequence<W>() {

			@Override
			protected BigInteger abstractGetLength() {
				return AbstractSequence.getLength(source);
			}

			@Override
			public Iterator<W> iterator() {
				return new Iterator<W>() {

					private final Iterator<V> iterator = source.iterator();

					@Override
					public boolean hasNext() {
						return this.iterator.hasNext();
					}

					@Override
					public W next() {
						return mapping.map(this.iterator.next());
					}
				};
			}

		};
	}

	@Override
	public final Sequence<V> filter(final Predicate<? super V> predicate) {
		if (predicate == null) {
			throw new IllegalArgumentException();
		}
		final Sequence<V> source = this;
		return new AbstractSequence<V>() {

			@Override
			protected BigInteger abstractGetLength() {
				if (source.isEmpty()) {
					return MathUtil.ZERO;
				}
				return Sequence.UNKNOWN;
			}

			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {

					private final Iterator<V> iterator = source.iterator();
					private V nextValue = AbstractSequence.getNextValue(this.iterator, predicate);

					@Override
					public boolean hasNext() {
						return this.nextValue != null;
					}

					@Override
					public V next() {
						V result = this.nextValue;
						this.nextValue = AbstractSequence.getNextValue(this.iterator, predicate);
						return result;
					}

				};
			}
		};

	}

	@Override
	public final Sequence<V> shorten(final long maxLength) {
		return this.shorten(BigInteger.valueOf(maxLength));
	}

	@Override
	public final Sequence<V> shorten(final BigInteger maxLength) {
		if (maxLength == null || maxLength.signum() < 0) {
			throw new IllegalArgumentException();
		}
		final Sequence<V> source = this;
		return new AbstractSequence<V>() {

			@Override
			protected BigInteger abstractGetLength() {
				BigInteger length = source.getLength();
				if (length.equals(Sequence.INFINITE) || maxLength.signum() == 0) {
					return maxLength;
				}
				if (length.equals(Sequence.UNKNOWN)) {
					return Sequence.UNKNOWN;
				}
				return length.min(maxLength);
			}

			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {

					private final Iterator<V> iterator = source.iterator();
					private BigInteger counter = MathUtil.ZERO;

					@Override
					public boolean hasNext() {
						return this.counter.compareTo(maxLength) < 0 && this.iterator.hasNext();
					}

					@Override
					public V next() {
						this.counter = this.counter.add(MathUtil.ONE);
						return this.iterator.next();
					}

				};
			}

		};
	}

	@Override
	public Sequence<V> skip() {
		return this.skip(1);
	}

	@Override
	public Sequence<V> skip(final long n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		final Sequence<V> source = this;
		return new AbstractSequence<V>() {

			@Override
			protected BigInteger abstractGetLength() {
				BigInteger length = source.getLength();
				if (length.equals(Sequence.INFINITE)) {
					return Sequence.INFINITE;
				}
				if (length.equals(Sequence.UNKNOWN)) {
					return Sequence.UNKNOWN;
				}
				return source.getLength().subtract(BigInteger.valueOf(n)).max(MathUtil.ZERO);
			}

			@Override
			public Iterator<V> iterator() {
				Iterator<V> iterator = source.iterator();
				long i = n;
				while (i > 0 && iterator.hasNext()) {
					iterator.next();
					i--;
				}
				return iterator;
			}
		};
	}

	@Override
	public final Sequence<DenseArray<V>> group(final long groupLength) {
		if (groupLength < 1) {
			throw new IllegalArgumentException();
		}
		final Sequence<V> source = this;
		return new AbstractSequence<DenseArray<V>>() {

			@Override
			protected BigInteger abstractGetLength() {
				BigInteger length = source.getLength();
				if (length.equals(Sequence.INFINITE)) {
					return Sequence.INFINITE;
				}
				if (length.equals(Sequence.UNKNOWN)) {
					return Sequence.UNKNOWN;
				}
				return MathUtil.divideUp(length, BigInteger.valueOf(groupLength));
			}

			@Override
			public Iterator<DenseArray<V>> iterator() {
				return new Iterator<DenseArray<V>>() {

					private final Iterator<V> iterator = source.iterator();

					@Override
					public boolean hasNext() {
						return this.iterator.hasNext();
					}

					@Override
					public DenseArray<V> next() {
						long i = 0;
						DenseArray<V> result = DenseArray.getInstance();
						while (i < groupLength && this.iterator.hasNext()) {
							result = result.add(this.iterator.next());
							i++;
						}
						return result;
					}

				};

			}
		};
	}

	protected abstract BigInteger abstractGetLength();

	// helper method used to compute the length of various sources
	protected static <V> BigInteger getLength(Iterable<V> source) {
		if (source instanceof Sequence) {
			return ((Sequence<V>) source).getLength();
		}
		if (source instanceof Collection) {
			return BigInteger.valueOf(((Collection<V>) source).size());
		}
		if (source.iterator().hasNext()) {
			return Sequence.UNKNOWN;
		}
		return MathUtil.ZERO;
	}

	private static <V> V getNextValue(Iterator<V> iterator, Predicate<? super V> predicate) {
		V nextValue = null;
		while (nextValue == null && iterator.hasNext()) {
			V value = iterator.next();
			if (predicate.test(value)) {
				nextValue = value;
			}
		}
		return nextValue;
	}

	public static <V> Sequence<V> getInstance(final V... source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		return new AbstractSequence<V>() {

			@Override
			protected BigInteger abstractGetLength() {
				return BigInteger.valueOf(source.length);
			}

			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {
					private int pos = 0;

					@Override
					public boolean hasNext() {
						return this.pos < source.length;
					}

					@Override
					public V next() {
						return source[this.pos++];
					}

				};
			}
		};
	}

	public static <V> Sequence<V> getInstance(final ImmutableArray<V> source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		return AbstractSequence.getInstance(source, BigInteger.valueOf(source.getLength()));
	}

	public static <V> Sequence<V> getInstance(final Collection<V> source) {
		if (source == null) {
			throw new IllegalArgumentException();
		}
		return AbstractSequence.getInstance(source, BigInteger.valueOf(source.size()));
	}

	private static <V> Sequence<V> getInstance(final Iterable<V> source, final BigInteger length) {
		return new AbstractSequence<V>() {

			@Override
			protected BigInteger abstractGetLength() {
				return length;
			}

			@Override
			public Iterator<V> iterator() {
				return source.iterator();
			}
		};
	}

}
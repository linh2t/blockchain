/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
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
package ch.bfh.unicrypt.helper.tree;

import ch.bfh.unicrypt.helper.aggregator.interfaces.Aggregator;
import ch.bfh.unicrypt.helper.iterable.IterableArray;
import java.util.Iterator;

/**
 * An instances of this class represents an internal node of a {@link Tree}. The recursive definition of a tree implies
 * that a node is a tree on its own. In other words, a node connects several sub-trees (its children) to a new tree. The
 * number of children is not restricted and may even be 0.
 * <p>
 * @author R. Haenni
 * @version 2.0
 * @param <V> The generic type of the values stored in the leaves of the tree
 * @see Tree
 * @see Leaf
 */
public class Node<V>
	   extends Tree<V> {

	private final Iterable<Tree<V>> children;

	private Node(Iterable<Tree<V>> children) {
		this.children = children;
	}

	/**
	 * Creates a new node from a given iterable collection of sub-trees (its children) of type {@code V}.
	 * <p>
	 * @param <V>      The generic type of the tree
	 * @param children The given sub-trees
	 * @return The new node
	 */
	public static <V> Node<V> getInstance(Iterable<Tree<V>> children) {
		if (children == null) {
			throw new IllegalArgumentException();
		}
		for (Tree<V> child : children) {
			if (child == null) {
				throw new IllegalArgumentException();
			}
		}
		return new Node<V>(children);
	}

	/**
	 * Creates a new node from a given array of sub-trees (its children) of type {@code V}. This is a convenience method
	 * for {@link Node#getInstance(java.lang.Iterable)}.
	 * <p>
	 * @param <V>      The generic type of the tree
	 * @param children The given array of sub-trees
	 * @return The new node
	 */
	public static <V> Node<V> getInstance(Tree<V>... children) {
		return Node.getInstance(IterableArray.getInstance(children));
	}

	/**
	 * Returns an iterable collection of the node's children.
	 * <p>
	 * @return The node's children
	 */
	public Iterable<Tree<V>> getChildren() {
		return this.children;
	}

	@Override
	public V abstractAggregate(final Aggregator<V> aggregator) {
		Iterable<V> childrenValues = new Iterable<V>() {

			@Override
			public Iterator<V> iterator() {
				return new Iterator<V>() {

					Iterator<Tree<V>> childrenIterator = children.iterator();

					@Override
					public boolean hasNext() {
						return childrenIterator.hasNext();
					}

					@Override
					public V next() {
						return childrenIterator.next().abstractAggregate(aggregator);
					}
				};
			}
		};
		return aggregator.aggregate(childrenValues);
	}

	@Override
	public Iterator<V> iterator() {

		return new Iterator<V>() {

			Iterator<Tree<V>> childrenIterator = children.iterator();
			Tree<V> child = null;
			Iterator<V> childIterator = null;

			{
				if (childrenIterator.hasNext()) {
					do {
						this.child = childrenIterator.next();
						this.childIterator = this.child.iterator();
					} while (!childIterator.hasNext() && childrenIterator.hasNext());
				}
			}

			@Override
			public boolean hasNext() {
				return child != null && childIterator.hasNext();
			}

			@Override
			public V next() {
				V next = childIterator.next();
				if (!childIterator.hasNext()) {
					if (childrenIterator.hasNext()) {
						do {
							this.child = childrenIterator.next();
							this.childIterator = this.child.iterator();
						} while (!childIterator.hasNext() && childrenIterator.hasNext());
					}
				}
				return next;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};

	}

	@Override
	protected String defaultToStringContent() {
		String sep = "";
		String result = "[";
		for (Tree<V> child : this.children) {
			result = result + sep + child.defaultToStringContent();
			sep = "|";
		}
		return result + "]";
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 11 * hash + this.children.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Node<?> other = (Node<?>) obj;
		return equalIterators(this.children.iterator(), other.children.iterator());
	}

	private static boolean equalIterators(Iterator<?> it1, Iterator<?> it2) {
		while (it1.hasNext() && it2.hasNext()) {
			if (!it1.next().equals(it2.next())) {
				return false;
			}
		}
		return (!it1.hasNext() && !it2.hasNext());
	}

}
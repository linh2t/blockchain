/*
 * UniCrypt
 *
 *  UniCrypt(tm): Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (c) 2016 Bern University of Applied Sciences (BFH), Research Institute for
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
package ch.bfh.unicrypt.math.algebra.concatenative.abstracts;

import ch.bfh.unicrypt.ErrorCode;
import ch.bfh.unicrypt.UniCryptRuntimeException;
import ch.bfh.unicrypt.math.algebra.concatenative.interfaces.ConcatenativeElement;
import ch.bfh.unicrypt.math.algebra.concatenative.interfaces.ConcatenativeMonoid;
import ch.bfh.unicrypt.math.algebra.concatenative.interfaces.ConcatenativeSemiGroup;
import ch.bfh.unicrypt.math.algebra.general.abstracts.AbstractElement;
import ch.bfh.unicrypt.math.algebra.general.abstracts.AbstractSet;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import java.math.BigInteger;

/**
 * This abstract class provides a basis implementation for objects of type {@link ConcatenativeElement}.
 * <p>
 * @param <S> The generic type of the {@link ConcatenativeSemiGroup} of this element
 * @param <E> The generic type of this element
 * @param <V> The generic type of the value stored in this element
 * <p>
 * @author R. Haenni
 */
public abstract class AbstractConcatenativeElement<S extends ConcatenativeSemiGroup<V>, E extends ConcatenativeElement<V>, V>
	   extends AbstractElement<S, E, V>
	   implements ConcatenativeElement<V> {

	private static final long serialVersionUID = 1L;

	protected AbstractConcatenativeElement(final AbstractSet<E, V> semiGroup, final V value) {
		super(semiGroup, value);
	}

	@Override
	public final E concatenate(final Element element) {
		return (E) this.getSet().concatenate(this, element);
	}

	@Override
	public final E selfConcatenate(final BigInteger amount) {
		return (E) this.getSet().selfConcatenate(this, amount);
	}

	@Override
	public final E selfConcatenate(final Element<BigInteger> amount) {
		return (E) this.getSet().selfConcatenate(this, amount);
	}

	@Override
	public final E selfConcatenate(final long amount) {
		return (E) this.getSet().selfConcatenate(this, amount);
	}

	@Override
	public final E selfConcatenate() {
		return (E) this.getSet().selfConcatenate(this);
	}

	@Override
	public boolean isEmptyElement() {
		if (this.getSet().isMonoid()) {
			ConcatenativeMonoid monoid = ((ConcatenativeMonoid) this.getSet());
			return monoid.isEmptyElement(this);
		}
		throw new UniCryptRuntimeException(ErrorCode.UNSUPPORTED_OPERATION, this);
	}

}

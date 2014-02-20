/*
 * UniCrypt
 *
 *  UniCrypt(tm) : Cryptographical framework allowing the implementation of cryptographic protocols e.g. e-voting
 *  Copyright (C) 2014 Bern University of Applied Sciences (BFH), Research Institute for
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
package ch.bfh.unicrypt.math.algebra.dualistic.classes;

import ch.bfh.unicrypt.math.algebra.dualistic.interfaces.DualisticElement;
import ch.bfh.unicrypt.math.algebra.dualistic.interfaces.Ring;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.helper.polynomial.GenericPolynomial;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rolfhaenni
 * @param <V>
 */
public class PolynomialRing<V extends Object>
	   extends PolynomialSemiRing<V>
	   implements Ring<GenericPolynomial<DualisticElement<V>>> {

	protected PolynomialRing(Ring ring) {
		super(ring);
	}

	public Ring<V> getRing() {
		return (Ring<V>) super.getSemiRing();
	}

	//
	// The following protected methods override the default implementation from
	// various super-classes
	//
	@Override
	public PolynomialElement<V> invert(Element element) {
		Map<Integer, DualisticElement<V>> coefficientMap = new HashMap<Integer, DualisticElement<V>>();
		GenericPolynomial<DualisticElement<V>> polynomial = ((PolynomialElement<V>) element).getValue();
		for (Integer i : polynomial.getIndices()) {
			coefficientMap.put(i, polynomial.getCoefficient(i).negate());
		}
		return this.getElement(coefficientMap);
	}

	@Override
	public PolynomialElement<V> applyInverse(Element element1, Element element2) {
		return this.apply(element1, this.invert(element2));
	}

	@Override
	public PolynomialElement<V> subtract(Element element1, Element element2) {
		return this.applyInverse(element1, element2);
	}

	@Override
	public PolynomialElement<V> negate(Element element) {
		return this.invert(element);
	}

	//
	// STATIC FACTORY METHODS
	//
	public static PolynomialRing getInstance(Ring ring) {
		if (ring == null) {
			throw new IllegalArgumentException();
		}
		return new PolynomialRing(ring);
	}

}

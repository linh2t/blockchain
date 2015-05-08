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

import ch.bfh.unicrypt.helper.aggregator.classes.StringAggregator;
import java.math.BigInteger;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author rolfhaenni
 */
public class LeafTest {

	@Test
	public void generalLeafTest() {

		StringAggregator aggregator1 = StringAggregator.getInstance();
		StringAggregator aggregator2 = StringAggregator.getInstance('>', '(', ')', '/');

		String[] strings = new String[]{"Hello", "", "\\", "|", "||", "\\|", "\\\\", "[", "]", "\\[]|\\\\|[\\]"};

		for (String string : strings) {
			Leaf<String> leaf = Leaf.getInstance("Hello");
			Assert.assertEquals("Hello", leaf.getValue());

			Assert.assertEquals(leaf, Tree.getInstance(leaf.aggregate(aggregator1), aggregator1));
			Assert.assertEquals(leaf, Tree.getInstance(leaf.aggregate(aggregator2), aggregator2));
		}

		Leaf<String> leaf1 = Leaf.getInstance("Hello");
		Leaf<String> leaf2 = Leaf.getInstance("Hello");
		Leaf<String> leaf3 = Leaf.getInstance("World");
		Leaf<BigInteger> leaf4 = Leaf.getInstance(BigInteger.ONE);
		Assert.assertTrue(leaf1.equals(leaf1));
		Assert.assertTrue(leaf1.equals(leaf2));
		Assert.assertFalse(leaf1.equals(leaf3));
		Assert.assertFalse(leaf1.equals(leaf4));
	}

}
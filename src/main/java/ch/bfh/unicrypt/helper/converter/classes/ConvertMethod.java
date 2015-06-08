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
package ch.bfh.unicrypt.helper.converter.classes;

import ch.bfh.unicrypt.helper.UniCrypt;
import ch.bfh.unicrypt.helper.converter.interfaces.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this generic class provide multiple converters for the same output type {@code W}. The purpose of this
 * class is to declare the conversion into values of type {@code W} in one central place, without limiting the
 * flexibility. Note that {@link ConvertMethod} itself is not a {@link Converter}, it only allows the selection of the
 * converter to be used for instances of a specific class.
 * <p>
 * @author Rolf Haenni
 * @version 2.0
 * @param <W> The output type
 */
public class ConvertMethod<W extends Object>
	   extends UniCrypt {
	private static final long serialVersionUID = 1L;

	// a map for storing the converters
	private final Map<Class<?>, Converter<?, W>> converterMap;

	private ConvertMethod() {
		this.converterMap = new HashMap<Class<?>, Converter<?, W>>();
	}

	/**
	 * Creates a new converter method of output type {@code W} from a given list of converters of output type {@code W}.
	 * Each of the given converters must know the class of the input values (if the input class is unknown,
	 * {@link Converter#getInputClass()} returns {@code null}).
	 * <p>
	 * @param <W>        The output type
	 * @param converters A list of converters
	 * @return The new converter method
	 */
	public static <W> ConvertMethod<W> getInstance(Converter<?, W>... converters) {
		if (converters == null) {
			throw new IllegalArgumentException();
		}
		ConvertMethod convertMethod = new ConvertMethod();
		for (Converter<?, W> converter : converters) {
			if (converter == null) {
				throw new IllegalArgumentException();
			}
			convertMethod.addConverter(converter);
		}
		return convertMethod;
	}

	/**
	 * Selects and returns the converter for input values of a given class. Returns {@code null} if no such converter
	 * exists.
	 * <p>
	 * @param valueClass The class of the input values
	 * @return The corresponding converter (or {@code null} if no such converter exists)
	 */
	public Converter<?, W> getConverter(Class<?> valueClass) {
		return this.converterMap.get(valueClass);
	}

	private void addConverter(Converter<?, W> converter) {
		Class<?> valueClass = converter.getInputClass();
		if (valueClass == null || this.converterMap.containsKey(valueClass)) {
			throw new IllegalArgumentException();
		}
		this.converterMap.put(valueClass, converter);
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + (this.converterMap != null ? this.converterMap.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ConvertMethod<?> other = (ConvertMethod<?>) obj;
		if (this.converterMap.size() != other.converterMap.size()) {
			return false;
		}
		for (Class c : this.converterMap.keySet()) {
			if (!this.getConverter(c).equals(other.getConverter(c))) {
				return false;
			}
		}
		return true;
	}

}

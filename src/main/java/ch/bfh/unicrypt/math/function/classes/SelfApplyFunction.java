package ch.bfh.unicrypt.math.function.classes;

import java.util.Random;

import ch.bfh.unicrypt.math.algebra.dualistic.classes.Naturals;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductSet;
import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Group;
import ch.bfh.unicrypt.math.algebra.general.interfaces.SemiGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.function.abstracts.AbstractFunction;

/**
 * This class represents the the concept of a function f:XxZ->Y, where Z is an
 * atomic group. The second input element can thus be transformed into an
 * integer value z, which determines the number of times the group operation is
 * applied to the first input element.
 *
 * @see Group#selfApply(Element, Element)
 * @see Element#selfApply(Element)
 *
 * @author R. Haenni
 * @author R. E. Koenig
 * @version 1.0
 */
public class SelfApplyFunction extends AbstractFunction<ProductSet, SemiGroup, Element> {

  private SelfApplyFunction(final ProductSet domain, final SemiGroup coDomain) {
    super(domain, coDomain);
  }

  //
  // The following protected method implements the abstract method from {@code AbstractFunction}
  //
  @Override
  protected Element abstractApply(final Element element, final Random random) {
    Tuple tuple = (Tuple) element;
    return tuple.getAt(0).selfApply(tuple.getAt(1));
  }

  //
  // STATIC FACTORY METHODS
  //
  /**
   * This is a special constructor, where the group of the second parameter is
   * selected automatically from the given group.
   *
   * @param semiGroup The underlying group
   * @throws IllegalArgumentException if {@code group} is null
   */
  public static SelfApplyFunction getInstance(final SemiGroup semiGroup) {
    if (semiGroup == null) {
      throw new IllegalArgumentException();
    }
    if (semiGroup.isFinite() && semiGroup.hasKnownOrder()) {
      return SelfApplyFunction.getInstance(semiGroup, semiGroup.getZModOrder());
    }
    return SelfApplyFunction.getInstance(semiGroup, Naturals.getInstance());
  }

  /**
   * This is the general constructor of this class. The first parameter is the
   * group on which it operates, and the second parameter is the atomic group,
   * from which an element is needed to determine the number of times the group
   * operation is applied.
   *
   * @param semiGroup The underlying group
   * @param amountSet
   * @throws IllegalArgumentException if {@code group} is null
   * @throws IllegalArgumentException if {@code amountGroup} is negative
   */
  public static SelfApplyFunction getInstance(final SemiGroup semiGroup, final Set amountSet) {
    if (semiGroup == null || amountSet == null) {
      throw new IllegalArgumentException();
    }
    return new SelfApplyFunction(ProductSet.getInstance(semiGroup, amountSet), semiGroup);
  }

}

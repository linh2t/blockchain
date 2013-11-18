/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bfh.unicrypt.crypto.schemes.encryption.classes;

import ch.bfh.unicrypt.crypto.keygenerator.classes.ElGamalKeyPairGenerator;
import ch.bfh.unicrypt.crypto.keygenerator.interfaces.KeyPairGenerator;
import ch.bfh.unicrypt.crypto.schemes.encryption.abstracts.AbstractReEncryptionScheme;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZMod;
import ch.bfh.unicrypt.math.algebra.dualistic.classes.ZModPrime;
import ch.bfh.unicrypt.math.algebra.general.classes.ProductGroup;
import ch.bfh.unicrypt.math.algebra.general.classes.Tuple;
import ch.bfh.unicrypt.math.algebra.general.interfaces.CyclicGroup;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarMod;
import ch.bfh.unicrypt.math.algebra.multiplicative.classes.GStarModElement;
import ch.bfh.unicrypt.math.function.classes.ApplyFunction;
import ch.bfh.unicrypt.math.function.classes.ApplyInverseFunction;
import ch.bfh.unicrypt.math.function.classes.CompositeFunction;
import ch.bfh.unicrypt.math.function.classes.GeneratorFunction;
import ch.bfh.unicrypt.math.function.classes.MultiIdentityFunction;
import ch.bfh.unicrypt.math.function.classes.ProductFunction;
import ch.bfh.unicrypt.math.function.classes.RemovalFunction;
import ch.bfh.unicrypt.math.function.classes.SelectionFunction;
import ch.bfh.unicrypt.math.function.classes.SelfApplyFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;

/**
 *
 * @author rolfhaenni
 * @param <MS>
 * @param <ME>
 */
public class ElGamalEncryptionScheme<MS extends CyclicGroup, ME extends Element>
       extends AbstractReEncryptionScheme<MS, ProductGroup, ME, Tuple, CyclicGroup, ZModPrime, ZModPrime> {

  private final MS cyclicGroup;
  private final ME generator;
  private Function encryptionFunctionLeft;
  private Function encryptionFunctionRight;

//  protected ElGamalEncryptionScheme(Function encryptionFunction, Function decryptionFunction, ElGamalKeyPairGenerator keyPairGenerator, Function encryptionFunctionLeft, Function encryptionFunctionRight) {
  protected ElGamalEncryptionScheme(MS cyclicGroup, ME generator) {
    this.cyclicGroup = cyclicGroup;
    this.generator = generator;
  }

  public final MS getCyclicGroup() {
    return this.cyclicGroup;
  }

  public final ME getGenerator() {
    return this.generator;
  }

  @Override
  protected Function abstractGetEncryptionFunction() {
    ZMod zMod = this.cyclicGroup.getZModOrder();
    ProductGroup encryptionDomain = ProductGroup.getInstance(this.cyclicGroup, this.cyclicGroup, zMod);
    return CompositeFunction.getInstance(
           MultiIdentityFunction.getInstance(encryptionDomain, 2),
           ProductFunction.getInstance(CompositeFunction.getInstance(SelectionFunction.getInstance(encryptionDomain, 2),
                                                                     this.getEncryptionFunctionLeft()),
                                       this.getEncryptionFunctionRight()));
  }

  @Override
  protected Function abstractGetDecryptionFunction() {
    ZMod zMod = this.cyclicGroup.getZModOrder();
    ProductGroup decryptionDomain = ProductGroup.getInstance(zMod, ProductGroup.getInstance(this.cyclicGroup, 2));
    return CompositeFunction.getInstance(
           MultiIdentityFunction.getInstance(decryptionDomain, 2),
           ProductFunction.getInstance(SelectionFunction.getInstance(decryptionDomain, 1, 1),
                                       CompositeFunction.getInstance(MultiIdentityFunction.getInstance(decryptionDomain, 2),
                                                                     ProductFunction.getInstance(SelectionFunction.getInstance(decryptionDomain, 1, 0),
                                                                                                 SelectionFunction.getInstance(decryptionDomain, 0)),
                                                                     SelfApplyFunction.getInstance(this.cyclicGroup, zMod))),
           ApplyInverseFunction.getInstance(this.cyclicGroup));
  }

  @Override
  protected KeyPairGenerator abstractGetKeyPairGenerator() {
    return ElGamalKeyPairGenerator.getInstance(this.getGenerator());
  }

  @Override
  protected String standardToStringContent() {
    return this.getMessageSpace().toString();
  }

  public Function getEncryptionFunctionLeft() {
    if (this.encryptionFunctionLeft == null) {
      this.encryptionFunctionLeft = GeneratorFunction.getInstance(this.getGenerator());
    }
    return this.encryptionFunctionLeft;
  }

  public Function getEncryptionFunctionRight() {
    if (this.encryptionFunctionRight == null) {
      ZMod zMod = this.cyclicGroup.getZModOrder();
      ProductGroup encryptionDomain = ProductGroup.getInstance(this.cyclicGroup, this.cyclicGroup, zMod);
      this.encryptionFunctionRight = CompositeFunction.getInstance(
             MultiIdentityFunction.getInstance(encryptionDomain, 2),
             ProductFunction.getInstance(SelectionFunction.getInstance(encryptionDomain, 1),
                                         CompositeFunction.getInstance(RemovalFunction.getInstance(encryptionDomain, 1),
                                                                       SelfApplyFunction.getInstance(cyclicGroup))),
             ApplyFunction.getInstance(this.cyclicGroup));
    }
    return this.encryptionFunctionRight;
  }

  public static <MS extends CyclicGroup, ME extends Element> ElGamalEncryptionScheme<MS, ME> getInstance(MS cyclicGroup) {
    return new ElGamalEncryptionScheme<MS, ME>(cyclicGroup, (ME) cyclicGroup.getDefaultGenerator());
  }

  public static ElGamalEncryptionScheme<GStarMod, GStarModElement> getInstance(GStarMod gStarMod) {
    return ElGamalEncryptionScheme.<GStarMod, GStarModElement>getInstance(gStarMod);
  }

//  public static ElGamalEncryptionScheme<ECGroup, ECElement> getInstance(ECGroup ecGroup) {
//    return ElGamalEncryptionScheme.<ECGroup, ECElement>getInstance(ecGroup);
//  }
  public static <MS extends CyclicGroup, ME extends Element> ElGamalEncryptionScheme<MS, ME> getInstance(ME generator) {
    if (!generator.isGenerator()) {
      throw new IllegalArgumentException();
    }
    return new ElGamalEncryptionScheme<MS, ME>((MS) generator.getSet(), generator);
  }

  public static ElGamalEncryptionScheme<GStarMod, GStarModElement> getInstance(GStarModElement generator) {
    return ElGamalEncryptionScheme.<GStarMod, GStarModElement>getInstance(generator);
  }

}
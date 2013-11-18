package ch.bfh.unicrypt.crypto.encoder.abstracts;

import ch.bfh.unicrypt.crypto.encoder.interfaces.Encoder;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Element;
import ch.bfh.unicrypt.math.algebra.general.interfaces.Set;
import ch.bfh.unicrypt.math.function.interfaces.Function;

public abstract class AbstractEncoder<D extends Set, C extends Set, DE extends Element, CE extends Element>
       implements Encoder {

  private final Function encodingFunction;
  private final Function decodingFunction;

  protected AbstractEncoder(Function encodingFunction, Function decodingFunction) {
    this.encodingFunction = encodingFunction;
    this.decodingFunction = decodingFunction;
  }

  @Override
  public Function getEncodingFunction() {
    return this.encodingFunction;
  }

  @Override
  public Function getDecodingFunction() {
    return this.decodingFunction;
  }

  @Override
  public CE encode(final Element element) {
    return (CE) this.getEncodingFunction().apply(element);
  }

  @Override
  public DE decode(final Element element) {
    return (DE) this.getDecodingFunction().apply(element);
  }

  @Override
  public D getDomain() {
    return (D) this.getEncodingFunction().getDomain();
  }

  @Override
  public C getCoDomain() {
    return (C) this.getEncodingFunction().getCoDomain();
  }

}
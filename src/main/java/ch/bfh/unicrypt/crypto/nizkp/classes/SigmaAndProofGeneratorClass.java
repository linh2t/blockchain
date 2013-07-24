package ch.bfh.unicrypt.crypto.nizkp.classes;

import java.util.List;
import java.util.Random;

import ch.bfh.unicrypt.crypto.nizkp.abstracts.ProductProofGeneratorAbstract;
import ch.bfh.unicrypt.crypto.nizkp.interfaces.SigmaAndProofGenerator;
import ch.bfh.unicrypt.crypto.nizkp.interfaces.SigmaProofGenerator;
import ch.bfh.unicrypt.math.element.Element;
import ch.bfh.unicrypt.math.element.interfaces.TupleElement;
import ch.bfh.unicrypt.math.function.classes.ConcatenateFunction.ConcatParameter;
import ch.bfh.unicrypt.math.function.classes.HashFunction.HashAlgorithm;
import ch.bfh.unicrypt.math.function.classes.ProductFunction;
import ch.bfh.unicrypt.math.function.interfaces.Function;
import ch.bfh.unicrypt.math.function.interfaces.HashFunction;
import ch.bfh.unicrypt.math.function.interfaces.ProductFunction;
import ch.bfh.unicrypt.math.group.interfaces.ProductGroup;
import ch.bfh.unicrypt.math.utility.mapper.interfaces.Mapper;

public class SigmaAndProofGeneratorClass extends ProductProofGeneratorAbstract implements SigmaAndProofGenerator {

  private final SigmaProofGenerator sigmaProofGenerator;
  private final Function[] functions;

  public SigmaAndProofGeneratorClass(final List<Function> functions) {
    this(functions, SigmaProofGeneratorClass.DEFAULT_HASH_ALGORITHM, SigmaProofGeneratorClass.DEFAULT_CONCAT_ALGORITHM, SigmaProofGeneratorClass.DEFAULT_MAPPER);
  }

  public SigmaAndProofGeneratorClass(final List<Function> functions,
      final HashAlgorithm hashAlgorithm,
      final ConcatParameter concatParameter,
      final Mapper mapper) {
    this(functions.toArray(new Function[functions.size()]), hashAlgorithm, concatParameter, mapper);
  }

  public SigmaAndProofGeneratorClass(final Function... functions) {
    this(functions, SigmaProofGeneratorClass.DEFAULT_HASH_ALGORITHM, SigmaProofGeneratorClass.DEFAULT_CONCAT_ALGORITHM, SigmaProofGeneratorClass.DEFAULT_MAPPER);
  }

  public SigmaAndProofGeneratorClass(final Function[] functions, final HashAlgorithm hashAlgorithm, final ConcatParameter concatParameter, final Mapper mapper) {
    final Function function = new ProductFunction(functions);
    this.sigmaProofGenerator = new SigmaProofGeneratorClass(function, hashAlgorithm, concatParameter, mapper);
    this.functions = functions;
  }

  @Override
  public TupleElement generate(final Element secretInput, final Element publicInput, final Element otherInput, final Random random) {
    return this.sigmaProofGenerator.generate(secretInput, publicInput, otherInput, random);
  }

  @Override
  public boolean verify(final TupleElement proof, final Element publicInput, final Element otherInput) {
    return this.sigmaProofGenerator.verify(proof, publicInput, otherInput);
  }

  @Override
  public ProductGroup getProofSpace() {
    return this.sigmaProofGenerator.getProofSpace();
  }

  @Override
  public ProductFunction getProofFunction() {
    return (ProductFunction) this.sigmaProofGenerator.getProofFunction();
  }

  @Override
  public Function[] getFunctions() {
    return this.functions;
  }

  @Override
  public ProductGroup getResponseSpace() {
    return (ProductGroup) this.sigmaProofGenerator.getResponseSpace();
  }

  @Override
  public TupleElement getResponse(final TupleElement proof) {
    return (TupleElement) this.sigmaProofGenerator.getResponse(proof);
  }

  @Override
  public ProductGroup getCommitmentSpace() {
    return (ProductGroup) this.sigmaProofGenerator.getCommitmentSpace();
  }

  @Override
  public TupleElement getCommitment(final TupleElement proof) {
    return (TupleElement) this.sigmaProofGenerator.getCommitment(proof);
  }

  @Override
  public HashFunction getHashFunction() {
    return this.sigmaProofGenerator.getHashFunction();
  }

}
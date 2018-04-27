package ch.globaz.tmmas.personnesservice.domain.common;

import java.io.Serializable;

/**
 * Un ValueObject au sens DDD du terme.
 * 
 */
public interface ValueObject<T> extends Serializable {

  /**
   * Pas d'identité propre, comparaison par attributs
   *
   * @param other Le ValueObject à comparer
   * @return <code>true</code> si les attributs comparés sont egaux
   */
  boolean sameValueAs(T other);

}

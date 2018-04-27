package ch.globaz.tmmas.personnesservice.domain.common;

/**
 * Une entité au sens DDD du terme, caractérisé par:
 *  - une identité propre
 *  - un cycle de vie
 *  
 */
public interface Entity<T> {

  /**
   * Compraison par identité et non par les attributs
   *
   * @param other L'entité à comparer
   * @return true if the identities are the same, regardles of other attributes.
   */
  boolean sameIdentityAs(T other);

}

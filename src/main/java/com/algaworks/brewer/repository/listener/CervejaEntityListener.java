package com.algaworks.brewer.repository.listener;

import java.util.Objects;

import javax.persistence.PostLoad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.storage.FotoStorage;
import com.algaworks.brewer.storage.local.FotoStorageLocal;

/***
 * Toda vez que o JPA carregar uma cerveja no banco, após essa classe será
 * chamada A class cerveja está com uma anotação @EntityListeners passando essa
 * class.
 * 
 * @author Marques
 *
 */
public class CervejaEntityListener {

	@Autowired
	private FotoStorage fotoStorage;

	@PostLoad
	public void postLoad(final Cerveja cerveja) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(FotoStorage.class);
		if(Objects.isNull(fotoStorage)) {
			fotoStorage = new FotoStorageLocal();
		}
		if(Objects.nonNull(fotoStorage)) {			
			cerveja.setUrlFoto(fotoStorage.getUrl(cerveja.getFotoOuMock()));
			cerveja.setUrlThumbnailFoto(fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + cerveja.getFotoOuMock()));
		}
	}

}
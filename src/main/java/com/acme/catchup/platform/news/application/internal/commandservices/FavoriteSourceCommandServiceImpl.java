/**
 * FavoriteSourceCommandService implementation.
 *
 * @summary
 * The FavoriteSourceCommandServiceImpl class is an implementation of the FavoriteSourceCommandService interface.
 * It is responsible for handling the CreateFavoriteSourceCommand command.
 *
 */

package com.acme.catchup.platform.news.application.internal.commandservices;

import com.acme.catchup.platform.news.domain.model.aggregates.FavoriteSource;
import com.acme.catchup.platform.news.domain.model.commands.CreateFavoriteSourceCommand;
import com.acme.catchup.platform.news.domain.services.FavoriteSourceCommandService;
import com.acme.catchup.platform.news.infrastructure.persistance.jpa.FavoriteSourceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoriteSourceCommandServiceImpl implements FavoriteSourceCommandService {
    private final FavoriteSourceRepository favoriteSourceRepository;

    public FavoriteSourceCommandServiceImpl(FavoriteSourceRepository favoriteSourceRepository) {
        this.favoriteSourceRepository = favoriteSourceRepository;
    }

    /**
     * Handles the CreateFavoriteSourceCommand command.
     *
     * @param command - the CreateFavoriteSourceCommand command
     * @return an Optional of FavoriteSource
     */
    @Override
    public Optional<FavoriteSource> handle(CreateFavoriteSourceCommand command) {
        if (favoriteSourceRepository.existsByNewsApiKeyAndSourceId(command.newsApiKey(), command.sourceId())) {
            throw new IllegalArgumentException("Favorite source with same source ID already exists for this API key");
        }
        var favoriteSource = new FavoriteSource(command);
        var createdFavoriteSource = favoriteSourceRepository.save(favoriteSource);
        return Optional.of(createdFavoriteSource);
    }
}

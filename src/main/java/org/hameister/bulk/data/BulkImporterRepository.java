package org.hameister.bulk.data;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hameister on 19.12.17.
 */
public interface BulkImporterRepository extends JpaRepository<Item, String> {
}

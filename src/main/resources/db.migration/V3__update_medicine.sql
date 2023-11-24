ALTER TABLE `triguard`.`db_medicine`
    MODIFY `usage` TEXT NULL DEFAULT NULL,
    MODIFY `caution` TEXT NULL DEFAULT NULL,
    MODIFY `side_effect` TEXT NULL DEFAULT NULL,
    MODIFY `interaction` TEXT NULL DEFAULT NULL;
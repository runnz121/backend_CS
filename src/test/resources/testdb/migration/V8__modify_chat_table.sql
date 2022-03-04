ALTER TABLE `chat_log` ADD `created_at` DATETIME AFTER `message`;
ALTER TABLE `chat_log` ADD `modified_at` DATETIME AFTER `created_at`;
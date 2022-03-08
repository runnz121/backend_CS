ALTER TABLE `shop` ADD `open_time` TIMESTAMP AFTER `runtime`;
ALTER TABLE `shop` ADD `close_time` TIMESTAMP AFTER `open_time`;

CREATE TABLE `Users` (
  `id` int PRIMARY KEY,
  `password` varchar(255),
  `first_name` varchar(255),
  `last_name` varchar(255),
  `email` varchar(255),
  `phone` varchar(255),
  `profile_image` varchar(255)
);

CREATE TABLE `Movies` (
  `id` int PRIMARY KEY,
  `title` varchar(255),
  `duration` int,
  `year` int,
  `director` varchar(255),
  `category` varchar(255)
);

CREATE TABLE `Ratings` (
  `id` int PRIMARY KEY,
  `value` int,
  `comment` varchar(255),
  `date` datetime,
  `movie_id` int,
  `user_id` int
);

ALTER TABLE `Ratings` ADD FOREIGN KEY (`movie_id`) REFERENCES `Movies` (`id`);

ALTER TABLE `Ratings` ADD FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`);

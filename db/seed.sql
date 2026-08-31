-- Seed generado desde https://rickandmortyapi.com/api
-- Regenerar con: node scripts/fetch-rick-and-morty.mjs

BEGIN;

DROP TABLE IF EXISTS character_episodes, location_residents, characters, episodes, locations CASCADE;

CREATE TABLE locations (
  id integer PRIMARY KEY,
  name text NOT NULL,
  type text NOT NULL,
  dimension text NOT NULL DEFAULT ''
);

CREATE TABLE characters (
  id integer PRIMARY KEY,
  name text NOT NULL,
  status text NOT NULL,
  species text NOT NULL,
  type text NOT NULL DEFAULT '',
  gender text NOT NULL,
  origin_id integer REFERENCES locations (id) ON DELETE SET NULL,
  location_id integer REFERENCES locations (id) ON DELETE SET NULL,
  image text NOT NULL
);

CREATE TABLE episodes (
  id integer PRIMARY KEY,
  name text NOT NULL,
  air_date text NOT NULL,
  episode text NOT NULL
);

-- Relaciones N:M resueltas con tablas puente.
CREATE TABLE location_residents (
  location_id integer NOT NULL REFERENCES locations (id) ON DELETE CASCADE,
  character_id integer NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  PRIMARY KEY (location_id, character_id)
);

CREATE TABLE character_episodes (
  character_id integer NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  episode_id integer NOT NULL REFERENCES episodes (id) ON DELETE CASCADE,
  PRIMARY KEY (character_id, episode_id)
);

INSERT INTO locations (id, name, type, dimension) VALUES (1, 'Earth (C-137)', 'Planet', 'Dimension C-137');
INSERT INTO locations (id, name, type, dimension) VALUES (2, 'Abadango', 'Cluster', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (3, 'Citadel of Ricks', 'Space station', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (4, 'Worldender''s lair', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (5, 'Anatomy Park', 'Microverse', 'Dimension C-137');
INSERT INTO locations (id, name, type, dimension) VALUES (6, 'Interdimensional Cable', 'TV', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (7, 'Immortality Field Resort', 'Resort', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (8, 'Post-Apocalyptic Earth', 'Planet', 'Post-Apocalyptic Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (9, 'Purge Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (10, 'Venzenulon 7', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (11, 'Bepis 9', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (12, 'Cronenberg Earth', 'Planet', 'Cronenberg Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (13, 'Nuptia 4', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (14, 'Giant''s Town', 'Fantasy town', 'Fantasy Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (15, 'Bird World', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (16, 'St. Gloopy Noops Hospital', 'Space station', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (17, 'Earth (5-126)', 'Planet', 'Dimension 5-126');
INSERT INTO locations (id, name, type, dimension) VALUES (18, 'Mr. Goldenfold''s dream', 'Dream', 'Dimension C-137');
INSERT INTO locations (id, name, type, dimension) VALUES (19, 'Gromflom Prime', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (20, 'Earth (Replacement Dimension)', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (21, 'Testicle Monster Dimension', 'Dimension', 'Testicle Monster Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (22, 'Signus 5 Expanse', 'unknown', 'Cromulon Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (23, 'Earth (C-500A)', 'Planet', 'Dimension C-500A');
INSERT INTO locations (id, name, type, dimension) VALUES (24, 'Rick''s Battery Microverse', 'Microverse', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (25, 'The Menagerie', 'Menagerie', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (26, 'Earth (K-83)', 'Planet', 'Dimension K-83');
INSERT INTO locations (id, name, type, dimension) VALUES (27, 'Hideout Planet', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (28, 'Unity''s Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (29, 'Dorian 5', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (30, 'Earth (Unknown dimension)', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (31, 'Earth (J19ζ7)', 'Planet', 'Dimension J19ζ7');
INSERT INTO locations (id, name, type, dimension) VALUES (32, 'Roy: A Life Well Lived', 'Game', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (33, 'Eric Stoltz Mask Earth', 'Planet', 'Eric Stoltz Mask Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (34, 'Earth (Evil Rick''s Target Dimension)', 'Planet', 'Evil Rick''s Target Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (35, 'Planet Squanch', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (36, 'Glaagablaaga', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (37, 'Resort Planet', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (38, 'Interdimensional Customs', 'Customs', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (39, 'Galactic Federation Prison', 'Space station', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (40, 'Gazorpazorp', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (41, 'Hamster in Butt World', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (42, 'Earth (Giant Telepathic Spiders Dimension)', 'Planet', 'Giant Telepathic Spiders Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (43, 'Alphabetrium', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (44, 'Jerryboree', 'Daycare', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (45, 'Krootabulon', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (46, 'Zigerion''s Base', 'Space station', 'Dimension C-137');
INSERT INTO locations (id, name, type, dimension) VALUES (47, 'Pluto', 'Dwarf planet (Celestial Dwarf)', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (48, 'Fantasy World', 'Planet', 'Fantasy Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (49, 'Zeep Xanflorp''s Miniverse', 'Miniverse', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (50, 'Kyle''s Teenyverse', 'Teenyverse', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (51, 'Larva Alien''s Planet', 'Planet', 'Unknown dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (52, 'Earth (K-22)', 'Planet', 'Dimension K-22');
INSERT INTO locations (id, name, type, dimension) VALUES (53, 'Mr. Meeseeks Box', 'Box', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (54, 'Vindicator''s Base', 'Spacecraft', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (55, 'Pawn Shop Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (56, 'Mega Gargantuan Kingdom', 'Microverse', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (57, 'Gear World', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (58, 'Earth (D-99)', 'Planet', 'Dimension D-99');
INSERT INTO locations (id, name, type, dimension) VALUES (59, 'Earth (D716)', 'Planet', 'Dimension D716');
INSERT INTO locations (id, name, type, dimension) VALUES (60, 'Earth (D716-B)', 'Planet', 'Dimension D716-B');
INSERT INTO locations (id, name, type, dimension) VALUES (61, 'Earth (D716-C)', 'Planet', 'Dimension D716-C');
INSERT INTO locations (id, name, type, dimension) VALUES (62, 'Earth (J-22)', 'Planet', 'Dimension J-22');
INSERT INTO locations (id, name, type, dimension) VALUES (63, 'Froopyland', 'Artificially generated world', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (64, 'Detoxifier', 'Machine', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (65, 'Trunk World', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (66, 'Plopstar', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (67, 'Blips and Chitz', 'Arcade', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (68, 'Girvonesk', 'unknown', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (69, 'Earth (C-35)', 'Planet', 'Dimension C-35');
INSERT INTO locations (id, name, type, dimension) VALUES (70, 'Snuffles'' Dream', 'Dream', 'Dimension C-137');
INSERT INTO locations (id, name, type, dimension) VALUES (71, 'Earth (Pizza Dimension)', 'Planet', 'Pizza Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (72, 'Earth (Phone Dimension)', 'Planet', 'Phone Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (73, 'Greasy Grandma World', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (74, 'Earth (Chair Dimension)', 'Planet', 'Chair Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (75, 'Árboles Mentirosos', 'Planet', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (76, 'Alien Day Spa', 'Spa', 'unknown');
INSERT INTO locations (id, name, type, dimension) VALUES (77, 'Earth (Fascist Dimension)', 'Planet', 'Fascist Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (78, 'Snake Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (79, 'Forbodulon Prime', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (80, 'Earth (Fascist Shrimp Dimension)', 'Planet', 'Fascist Shrimp Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (81, 'Earth (Fascist Teddy Bear Dimension)', 'Planet', 'Fascist Teddy Bear Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (82, 'Earth (Wasp Dimension)', 'Planet', 'Wasp Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (83, 'Monogatron Mothership', 'Space station', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (84, 'Gorgon Quadrant', 'Quadrant', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (85, 'Midland Quasar', 'Quasar', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (86, 'Mount Space Everest', 'Mount', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (87, 'Globaflyn', 'Liquid', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (88, 'Heist-Con', 'Convention', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (89, 'Heistotron Base', 'Space station', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (90, 'Mount Olympus', 'Mount', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (91, 'Plitzville Montana', 'Woods', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (92, 'Earth (Tusk Dimension)', 'Planet', 'Tusk Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (93, 'Gramuflack', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (94, 'Draygon', 'Planet', 'Magic Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (95, 'New Improved Galactic Federation Quarters', 'Space station', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (96, 'Story Train', 'Diegesis', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (97, 'Non-Diegetic Alternative Reality', 'Non-Diegetic Alternative Reality', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (98, 'Tickets Please Guy Nightmare', 'Nightmare', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (99, 'Morty’s Story', 'Diegesis', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (100, 'Ricks’s Story', 'Diegesis', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (101, 'Glorzo Asteroid', 'Asteroid', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (102, 'Alien Acid Plant', 'Acid Plant', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (103, 'Merged Universe', 'Dimension', 'Merged Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (104, 'Near-Duplicate Reality', 'Reality', 'Unknown dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (105, 'NX-5 Planet Remover', 'Death Star', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (106, 'Gaia', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (107, 'Defiance''s Ship', 'Spacecraft', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (108, 'Defiance''s Base', 'Base', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (109, 'The Ocean', 'Liquid', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (110, 'Narnia Dimension', 'Dimension', 'Fantasy Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (111, 'Elemental Rings', 'Elemental Rings', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (112, 'Morglutz', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (113, 'Ferkus 9', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (114, 'Morty', 'Human', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (115, 'Space', 'Space', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (116, 'Hell', 'Hell', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (117, 'Z. Q. P. D.', 'Police Department', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (118, 'Space Tahoe', '', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (119, 'France', 'Country', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (120, 'Birdperson''s Consciousness', 'Consciousness', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (121, 'Rick''s Consciousness', 'Consciousness', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (122, 'Avian Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (123, 'Normal Size Bug Dimension', 'Dimension', '');
INSERT INTO locations (id, name, type, dimension) VALUES (124, 'Slartivart', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (125, 'Rick and Two Crows Planet', 'Planet', 'Replacement Dimension');
INSERT INTO locations (id, name, type, dimension) VALUES (126, 'Rick''s Memories', 'Memory', '');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (1, 'Rick Sanchez', 'Alive', 'Human', '', 'Male', 1, 3, 'https://rickandmortyapi.com/api/character/avatar/1.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (2, 'Morty Smith', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/2.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (3, 'Summer Smith', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/3.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (4, 'Beth Smith', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/4.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (5, 'Jerry Smith', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/5.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (6, 'Abadango Cluster Princess', 'Alive', 'Alien', '', 'Female', 2, 2, 'https://rickandmortyapi.com/api/character/avatar/6.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (7, 'Abradolf Lincler', 'unknown', 'Human', 'Genetic experiment', 'Male', 20, 21, 'https://rickandmortyapi.com/api/character/avatar/7.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (8, 'Adjudicator Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/8.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (9, 'Agency Director', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/9.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (10, 'Alan Rails', 'Dead', 'Human', 'Superhuman (Ghost trains summoner)', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/10.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (11, 'Albert Einstein', 'Dead', 'Human', '', 'Male', 1, 20, 'https://rickandmortyapi.com/api/character/avatar/11.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (12, 'Alexander', 'Dead', 'Human', '', 'Male', 1, 5, 'https://rickandmortyapi.com/api/character/avatar/12.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (13, 'Alien Googah', 'unknown', 'Alien', '', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/13.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (14, 'Alien Morty', 'unknown', 'Alien', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/14.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (15, 'Alien Rick', 'unknown', 'Alien', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/15.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (16, 'Amish Cyborg', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/16.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (17, 'Annie', 'Alive', 'Human', '', 'Female', 1, 5, 'https://rickandmortyapi.com/api/character/avatar/17.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (18, 'Antenna Morty', 'Alive', 'Human', 'Human with antennae', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/18.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (19, 'Antenna Rick', 'unknown', 'Human', 'Human with antennae', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/19.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (20, 'Ants in my Eyes Johnson', 'unknown', 'Human', 'Human with ants in his eyes', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/20.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (21, 'Aqua Morty', 'unknown', 'Humanoid', 'Fish-Person', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/21.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (22, 'Aqua Rick', 'unknown', 'Humanoid', 'Fish-Person', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/22.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (23, 'Arcade Alien', 'unknown', 'Alien', '', 'Male', NULL, 7, 'https://rickandmortyapi.com/api/character/avatar/23.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (24, 'Armagheadon', 'Alive', 'Alien', 'Cromulon', 'Male', 22, 22, 'https://rickandmortyapi.com/api/character/avatar/24.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (25, 'Armothy', 'Dead', 'unknown', 'Self-aware arm', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/25.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (26, 'Arthricia', 'Alive', 'Alien', 'Cat-Person', 'Female', 9, 9, 'https://rickandmortyapi.com/api/character/avatar/26.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (27, 'Artist Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/27.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (28, 'Attila Starwar', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/28.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (29, 'Baby Legs', 'Alive', 'Human', 'Human with baby legs', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/29.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (30, 'Baby Poopybutthole', 'Alive', 'Poopybutthole', '', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/30.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (31, 'Baby Wizard', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/31.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (32, 'Bearded Lady', 'Dead', 'Alien', 'Parasite', 'Female', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/32.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (33, 'Beebo', 'Dead', 'Alien', '', 'Male', 10, 10, 'https://rickandmortyapi.com/api/character/avatar/33.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (34, 'Benjamin', 'Alive', 'Poopybutthole', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/34.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (35, 'Bepisian', 'Alive', 'Alien', 'Bepisian', 'unknown', 11, 11, 'https://rickandmortyapi.com/api/character/avatar/35.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (36, 'Beta-Seven', 'Alive', 'Alien', 'Hivemind', 'unknown', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/36.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (37, 'Beth Sanchez', 'Alive', 'Human', '', 'Female', 23, 23, 'https://rickandmortyapi.com/api/character/avatar/37.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (38, 'Beth Smith', 'Alive', 'Human', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/38.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (39, 'Beth Smith', 'Alive', 'Human', '', 'Female', 34, 34, 'https://rickandmortyapi.com/api/character/avatar/39.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (40, 'Beth''s Mytholog', 'Dead', 'Mythological Creature', 'Mytholog', 'Female', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/40.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (41, 'Big Boobed Waitress', 'Alive', 'Mythological Creature', '', 'Female', 48, 48, 'https://rickandmortyapi.com/api/character/avatar/41.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (42, 'Big Head Morty', 'unknown', 'Human', 'Human with giant head', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/42.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (43, 'Big Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/43.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (44, 'Body Guard Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/44.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (45, 'Bill', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/45.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (46, 'Bill', 'unknown', 'Animal', 'Dog', 'Male', 20, NULL, 'https://rickandmortyapi.com/api/character/avatar/46.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (47, 'Birdperson', 'Alive', 'Alien', 'Bird-Person', 'Male', 15, 35, 'https://rickandmortyapi.com/api/character/avatar/47.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (48, 'Black Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/48.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (49, 'Blamph', 'Alive', 'Alien', '', 'unknown', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/49.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (50, 'Blim Blam', 'Alive', 'Alien', 'Korblock', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/50.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (51, 'Blue Diplomat', 'Alive', 'Alien', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/51.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (52, 'Blue Footprint Guy', 'Dead', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/52.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (53, 'Blue Shirt Morty', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/53.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (54, 'Bobby Moynihan', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/54.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (55, 'Boobloosian', 'Dead', 'Alien', 'Boobloosian', 'unknown', NULL, 13, 'https://rickandmortyapi.com/api/character/avatar/55.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (56, 'Bootleg Portal Chemist Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/56.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (57, 'Borpocian', 'Alive', 'Alien', 'Elephant-Person', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/57.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (58, 'Brad', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/58.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (59, 'Brad Anderson', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/59.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (60, 'Calypso', 'Dead', 'Human', 'Superhuman', 'Female', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/60.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (61, 'Campaign Manager Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/61.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (62, 'Canklanker Thom', 'Dead', 'Alien', 'Gromflomite', 'Male', 19, NULL, 'https://rickandmortyapi.com/api/character/avatar/62.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (63, 'Centaur', 'Alive', 'Mythological Creature', 'Centaur', 'Male', NULL, 18, 'https://rickandmortyapi.com/api/character/avatar/63.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (64, 'Chris', 'Dead', 'Alien', 'Organic gun', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/64.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (65, 'Chris', 'Alive', 'Humanoid', 'Microverse inhabitant', 'Male', 24, 24, 'https://rickandmortyapi.com/api/character/avatar/65.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (66, 'Coach Feratu (Balik Alistane)', 'Dead', 'Mythological Creature', 'Vampire', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/66.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (67, 'Collector', 'Alive', 'Alien', 'Light bulb-Alien', 'Male', 25, 25, 'https://rickandmortyapi.com/api/character/avatar/67.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (68, 'Colossus', 'Dead', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/68.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (69, 'Commander Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/69.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (70, 'Concerto', 'Dead', 'Humanoid', '', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/70.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (71, 'Conroy', 'Dead', 'Robot', '', 'unknown', 20, 1, 'https://rickandmortyapi.com/api/character/avatar/71.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (72, 'Cool Rick', 'Alive', 'Human', '', 'Male', 26, 3, 'https://rickandmortyapi.com/api/character/avatar/72.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (73, 'Cop Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/73.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (74, 'Cop Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/74.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (75, 'Courier Flap', 'Alive', 'Alien', '', 'unknown', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/75.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (76, 'Cousin Nicky', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/76.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (77, 'Cowboy Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/77.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (78, 'Cowboy Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/78.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (79, 'Crab Spider', 'Alive', 'Alien', 'Animal', 'unknown', 27, 27, 'https://rickandmortyapi.com/api/character/avatar/79.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (80, 'Creepy Little Girl', 'Alive', 'Human', '', 'Female', NULL, 18, 'https://rickandmortyapi.com/api/character/avatar/80.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (81, 'Crocubot', 'Dead', 'Animal', 'Robot-Crocodile hybrid', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/81.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (82, 'Cronenberg Rick', 'unknown', 'Cronenberg', '', 'Male', 12, 1, 'https://rickandmortyapi.com/api/character/avatar/82.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (83, 'Cronenberg Morty', 'unknown', 'Cronenberg', '', 'Male', 12, 1, 'https://rickandmortyapi.com/api/character/avatar/83.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (84, 'Cult Leader Morty', 'Alive', 'Human', '', 'Male', NULL, 27, 'https://rickandmortyapi.com/api/character/avatar/84.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (85, 'Cyclops Morty', 'Alive', 'Humanoid', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/85.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (86, 'Cyclops Rick', 'Dead', 'Humanoid', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/86.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (87, 'Cynthia', 'Dead', 'Alien', 'Zigerion', 'Female', NULL, 46, 'https://rickandmortyapi.com/api/character/avatar/87.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (88, 'Cynthia', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/88.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (89, 'Dale', 'Dead', 'Mythological Creature', 'Giant', 'Male', 14, 14, 'https://rickandmortyapi.com/api/character/avatar/89.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (90, 'Daron Jefferson', 'Alive', 'Alien', 'Cone-nippled alien', 'Male', 28, 28, 'https://rickandmortyapi.com/api/character/avatar/90.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (91, 'David Letterman', 'Alive', 'Human', '', 'Male', 23, 23, 'https://rickandmortyapi.com/api/character/avatar/91.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (92, 'Davin', 'Dead', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/92.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (93, 'Diablo Verde', 'Dead', 'Mythological Creature', 'Demon', 'Male', NULL, 29, 'https://rickandmortyapi.com/api/character/avatar/93.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (94, 'Diane Sanchez', 'Dead', 'Human', '', 'Female', 1, 126, 'https://rickandmortyapi.com/api/character/avatar/94.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (95, 'Dipper and Mabel Mortys', 'unknown', 'Human', '', 'unknown', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/95.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (96, 'Tuberculosis', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/96.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (97, 'Gonorrhea', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/97.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (98, 'Hepatitis A', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/98.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (99, 'Hepatitis C', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/99.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (100, 'Bubonic Plague', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/100.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (101, 'E. Coli', 'Dead', 'Disease', '', 'unknown', 5, 5, 'https://rickandmortyapi.com/api/character/avatar/101.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (102, 'Donna Gueterman', 'Dead', 'Robot', '', 'Female', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/102.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (103, 'Doofus Rick', 'unknown', 'Human', '', 'Male', 31, 20, 'https://rickandmortyapi.com/api/character/avatar/103.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (104, 'Doom-Nomitron', 'Dead', 'Alien', 'Shapeshifter', 'unknown', NULL, 29, 'https://rickandmortyapi.com/api/character/avatar/104.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (105, 'Dr. Glip-Glop', 'Dead', 'Alien', '', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/105.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (106, 'Dr. Schmidt', 'unknown', 'Human', 'Game', 'Male', 32, 32, 'https://rickandmortyapi.com/api/character/avatar/106.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (107, 'Dr. Wong', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/107.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (108, 'Dr. Xenon Bloom', 'Dead', 'Humanoid', 'Amoeba-Person', 'Male', NULL, 5, 'https://rickandmortyapi.com/api/character/avatar/108.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (109, 'Duck With Muscles', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/109.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (110, 'Eli', 'Alive', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/110.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (111, 'Eli''s Girlfriend', 'Alive', 'Human', '', 'Female', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/111.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (112, 'Eric McMan', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/112.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (113, 'Eric Stoltz Mask Morty', 'unknown', 'Human', '', 'Male', 33, 20, 'https://rickandmortyapi.com/api/character/avatar/113.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (114, 'Ethan', 'unknown', 'Human', 'Cronenberg', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/114.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (115, 'Ethan', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/115.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (116, 'Evil Beth Clone', 'Dead', 'Human', 'Clone', 'Female', NULL, 1, 'https://rickandmortyapi.com/api/character/avatar/116.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (117, 'Evil Jerry Clone', 'Dead', 'Human', 'Clone', 'Male', NULL, 1, 'https://rickandmortyapi.com/api/character/avatar/117.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (118, 'Evil Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/118.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (119, 'Evil Rick', 'Dead', 'Humanoid', 'Robot', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/119.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (120, 'Evil Summer Clone', 'Dead', 'Human', 'Clone', 'Female', NULL, 1, 'https://rickandmortyapi.com/api/character/avatar/120.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (121, 'Eyehole Man', 'Alive', 'Alien', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/121.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (122, 'Fart', 'Dead', 'Alien', 'Interdimensional gaseous being', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/122.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (123, 'Fat Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/123.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (124, 'Father Bob', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/124.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (125, 'Flansian', 'Alive', 'Alien', 'Flansian', 'unknown', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/125.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (126, 'Fleeb', 'unknown', 'Alien', '', 'unknown', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/126.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (127, 'Frank Palicky', 'Dead', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/127.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (128, 'Frankenstein''s Monster', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/128.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (129, 'Fulgora', 'Alive', 'Human', '', 'Female', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/129.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (130, 'Galactic Federation President', 'Dead', 'Alien', 'Gromflomite', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/130.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (131, 'Gar Gloonch', 'Dead', 'Alien', 'Zombodian', 'Male', NULL, 13, 'https://rickandmortyapi.com/api/character/avatar/131.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (132, 'Gar''s Mytholog', 'Dead', 'Mythological Creature', 'Mytholog', 'Male', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/132.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (133, 'Garblovian', 'Alive', 'Alien', 'Garblovian', 'Male', 36, NULL, 'https://rickandmortyapi.com/api/character/avatar/133.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (134, 'Garmanarnar', 'Alive', 'Alien', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/134.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (135, 'Garment District Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/135.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (136, 'Gazorpazorpfield', 'Alive', 'Alien', 'Gazorpian', 'Male', 40, 6, 'https://rickandmortyapi.com/api/character/avatar/136.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (137, 'Gene', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/137.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (138, 'General Nathan', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/138.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (139, 'General Store Owner', 'Dead', 'Alien', 'Cat-Person', 'Male', 9, 9, 'https://rickandmortyapi.com/api/character/avatar/139.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (140, 'Genital Washer', 'Alive', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/140.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (141, 'Ghost in a Jar', 'Dead', 'Alien', 'Parasite', 'Genderless', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/141.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (142, 'Gibble Snake', 'Dead', 'Alien', 'Animal', 'unknown', 37, 37, 'https://rickandmortyapi.com/api/character/avatar/142.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (143, 'Glasses Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/143.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (144, 'Glenn', 'Dead', 'Alien', 'Gromflomite', 'Male', NULL, 38, 'https://rickandmortyapi.com/api/character/avatar/144.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (145, 'Glenn', 'Alive', 'Human', 'Eat shiter-Person', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/145.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (146, 'Glexo Slim Slom', 'Alive', 'Alien', '', 'Male', NULL, 13, 'https://rickandmortyapi.com/api/character/avatar/146.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (147, 'Gobo', 'Dead', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/147.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (148, 'Goddess Beth', 'unknown', 'Mythological Creature', 'Goddess', 'Female', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/148.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (149, 'Gordon Lunas', 'Dead', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/149.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (150, 'Cornvelious Daniel', 'Dead', 'Alien', 'Gromflomite', 'Male', NULL, 39, 'https://rickandmortyapi.com/api/character/avatar/150.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (151, 'Gwendolyn', 'unknown', 'Robot', 'Gazorpian reproduction robot', 'Female', 40, 20, 'https://rickandmortyapi.com/api/character/avatar/151.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (152, 'Hammerhead Morty', 'unknown', 'Humanoid', 'Hammerhead-Person', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/152.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (153, 'Hamster In Butt', 'Alive', 'Animal', '', 'unknown', 41, 41, 'https://rickandmortyapi.com/api/character/avatar/153.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (154, 'Hamurai', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/154.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (155, 'Harold', 'Alive', 'Cronenberg', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/155.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (156, 'Hemorrhage', 'Alive', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/156.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (157, 'Hole in the Wall Where the Men Can See it All', 'unknown', 'unknown', 'Hole', 'Genderless', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/157.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (158, 'Hookah Alien', 'Alive', 'Alien', 'Tuskfish', 'unknown', NULL, 38, 'https://rickandmortyapi.com/api/character/avatar/158.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (159, 'Hunter', 'Dead', 'Human', 'Clone', 'Male', 42, 42, 'https://rickandmortyapi.com/api/character/avatar/159.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (160, 'Hunter''s Father', 'Alive', 'Human', '', 'Male', 42, 42, 'https://rickandmortyapi.com/api/character/avatar/160.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (161, 'Hydrogen-F', 'Alive', 'Alien', 'Alphabetrian', 'Female', 43, 43, 'https://rickandmortyapi.com/api/character/avatar/161.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (162, 'Ice-T', 'Alive', 'Alien', 'Alphabetrian', 'Male', 43, 43, 'https://rickandmortyapi.com/api/character/avatar/162.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (163, 'Ideal Jerry', 'Dead', 'Mythological Creature', 'Mytholog', 'Male', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/163.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (164, 'Insurance Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/164.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (165, 'Investigator Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/165.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (166, 'Invisi-trooper', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/166.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (167, 'Izzy', 'Alive', 'Animal', 'Cat', 'unknown', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/167.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (168, 'Jackie', 'Alive', 'Alien', 'Gazorpian', 'Female', 40, 40, 'https://rickandmortyapi.com/api/character/avatar/168.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (169, 'Jacob', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/169.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (170, 'Jacqueline', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/170.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (171, 'Jaguar', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/171.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (172, 'Jamey', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/172.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (173, 'Jan-Michael Vincent', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/173.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (174, 'Jerry 5-126', 'Alive', 'Human', '', 'Male', 17, 44, 'https://rickandmortyapi.com/api/character/avatar/174.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (175, 'Jerry Smith', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/175.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (176, 'Celebrity Jerry', 'Alive', 'Human', '', 'Male', 23, 23, 'https://rickandmortyapi.com/api/character/avatar/176.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (177, 'Jerry Smith', 'Alive', 'Human', '', 'Male', 34, 34, 'https://rickandmortyapi.com/api/character/avatar/177.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (178, 'Jerry''s Mytholog', 'Dead', 'Mythological Creature', 'Mytholog', 'Male', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/178.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (179, 'Jessica', 'Alive', 'Cronenberg', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/179.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (180, 'Jessica', 'Alive', 'Human', 'Time God', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/180.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (181, 'Jessica''s Friend', 'Alive', 'Human', '', 'Female', 1, 20, 'https://rickandmortyapi.com/api/character/avatar/181.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (182, 'Jim', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/182.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (183, 'Johnny Depp', 'Alive', 'Human', '', 'Male', 23, 23, 'https://rickandmortyapi.com/api/character/avatar/183.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (184, 'Jon', 'Alive', 'Alien', 'Gazorpian', 'Male', 40, 6, 'https://rickandmortyapi.com/api/character/avatar/184.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (185, 'Joseph Eli Lipkip', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/185.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (186, 'Joyce Smith', 'Alive', 'Human', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/186.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (187, 'Juggling Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/187.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (188, 'Karen Entity', 'Alive', 'Alien', 'Unknown-nippled alien', 'Female', 28, 28, 'https://rickandmortyapi.com/api/character/avatar/188.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (189, 'Katarina', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/189.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (190, 'Keara', 'Alive', 'Alien', 'Krootabulan', 'Female', 45, 20, 'https://rickandmortyapi.com/api/character/avatar/190.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (191, 'Kevin', 'Dead', 'Alien', 'Zigerion', 'Male', NULL, 46, 'https://rickandmortyapi.com/api/character/avatar/191.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (192, 'King Flippy Nips', 'Alive', 'Alien', 'Plutonian', 'Male', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/192.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (193, 'King Jellybean', 'Dead', 'Mythological Creature', 'Jellybean', 'Male', 48, 48, 'https://rickandmortyapi.com/api/character/avatar/193.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (194, 'Kozbian', 'Alive', 'Alien', 'Tentacle alien', 'unknown', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/194.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (195, 'Kristen Stewart', 'Alive', 'Human', '', 'Female', 23, 23, 'https://rickandmortyapi.com/api/character/avatar/195.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (196, 'Krombopulos Michael', 'Dead', 'Alien', 'Gromflomite', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/196.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (197, 'Kyle', 'Dead', 'Humanoid', 'Miniverse inhabitant', 'Male', 49, 50, 'https://rickandmortyapi.com/api/character/avatar/197.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (198, 'Lady Katana', 'Dead', 'Humanoid', 'Cyborg', 'Female', NULL, 29, 'https://rickandmortyapi.com/api/character/avatar/198.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (199, 'Larva Alien', 'Alive', 'Alien', 'Larva alien', 'unknown', 51, 35, 'https://rickandmortyapi.com/api/character/avatar/199.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (200, 'Lawyer Morty', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/200.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (201, 'Leonard Smith', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/201.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (202, 'Lighthouse Keeper', 'Dead', 'Alien', 'Cat-Person', 'Male', 9, 9, 'https://rickandmortyapi.com/api/character/avatar/202.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (203, 'Lil B', 'Dead', 'Alien', 'Snail alien', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/203.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (204, 'Lisa', 'Dead', 'Alien', '', 'Female', NULL, 7, 'https://rickandmortyapi.com/api/character/avatar/204.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (205, 'Little Dipper', 'Alive', 'Humanoid', 'Tinymouth', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/205.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (206, 'Lizard Morty', 'Alive', 'Humanoid', 'Lizard-Person', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/206.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (207, 'Loggins', 'Alive', 'Alien', 'Alligator-Person', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/207.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (208, 'Logic', 'Alive', 'Human', '', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/208.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (209, 'Long Sleeved Morty', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/209.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (210, 'Lucy', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/210.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (211, 'Ma-Sha', 'Alive', 'Alien', 'Gazorpian', 'Female', 40, 40, 'https://rickandmortyapi.com/api/character/avatar/211.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (212, 'Magma-Q', 'Dead', 'Alien', 'Alphabetrian', 'Male', 43, 43, 'https://rickandmortyapi.com/api/character/avatar/212.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (213, 'Magnesium-J', 'Alive', 'Alien', 'Alphabetrian', 'Male', 43, 43, 'https://rickandmortyapi.com/api/character/avatar/213.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (214, 'Man Painted Silver Who Makes Robot Noises', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/214.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (215, 'Maximums Rickimus', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/215.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (216, 'MC Haps', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/216.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (217, 'Mechanical Morty', 'Dead', 'Robot', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/217.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (218, 'Mechanical Rick', 'unknown', 'Robot', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/218.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (219, 'Mechanical Summer', 'unknown', 'Robot', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/219.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (220, 'Mega Fruit Farmer Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/220.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (221, 'Melissa', 'Alive', 'Mythological Creature', 'Monster', 'Female', 18, 18, 'https://rickandmortyapi.com/api/character/avatar/221.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (222, 'Michael Denny and the Denny Singers', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/222.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (223, 'Michael Jenkins', 'Dead', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/223.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (224, 'Michael McLick', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/224.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (225, 'Michael Thompson', 'Alive', 'Humanoid', 'Conjoined twin', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/225.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (226, 'Million Ants', 'Dead', 'Animal', 'Sentient ant colony', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/226.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (227, 'Mitch', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/227.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (228, 'Mohawk Guy', 'Dead', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/228.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (229, 'Morty Mart Manager Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/229.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (230, 'Morty Jr.', 'Alive', 'Humanoid', 'Human Gazorpian', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/230.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (231, 'Morty Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/231.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (232, 'Morty Smith', 'Alive', 'Human', '', 'Male', 34, 34, 'https://rickandmortyapi.com/api/character/avatar/232.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (233, 'Morty K-22', 'Alive', 'Human', '', 'Male', 52, 20, 'https://rickandmortyapi.com/api/character/avatar/233.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (234, 'Morty Smith', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/234.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (235, 'Mortytown Loco', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/235.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (236, 'Mr. Beauregard', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/236.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (237, 'Mr. Benson', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/237.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (238, 'Mr. Booby Buyer', 'Alive', 'Mythological Creature', 'Boobie buyer reptilian', 'Male', 48, 48, 'https://rickandmortyapi.com/api/character/avatar/238.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (239, 'Mr. Goldenfold', 'Alive', 'Cronenberg', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/239.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (240, 'Mr. Goldenfold', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/240.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (241, 'Mr. Marklovitz', 'Alive', 'Human', '', 'Male', 1, 20, 'https://rickandmortyapi.com/api/character/avatar/241.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (242, 'Mr. Meeseeks', 'unknown', 'Humanoid', 'Meeseeks', 'Male', 53, 77, 'https://rickandmortyapi.com/api/character/avatar/242.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (243, 'Mr. Needful', 'Alive', 'Humanoid', 'The Devil', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/243.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (244, 'Mr. Poopybutthole', 'Alive', 'Poopybutthole', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/244.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (245, 'Mrs. Lipkip', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/245.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (246, 'Mrs. Pancakes', 'Alive', 'Human', '', 'Female', 1, 18, 'https://rickandmortyapi.com/api/character/avatar/246.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (247, 'Amy Poopybutthole', 'Alive', 'Poopybutthole', '', 'Female', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/247.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (248, 'Mrs. Refrigerator', 'Dead', 'Alien', 'Parasite', 'Female', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/248.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (249, 'Mrs. Sanchez', 'unknown', 'Human', '', 'Female', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/249.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (250, 'Mrs. Sullivan', 'Dead', 'Human', 'Cat controlled dead lady', 'Female', 23, 6, 'https://rickandmortyapi.com/api/character/avatar/250.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (251, 'Nancy', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/251.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (252, 'Noob-Noob', 'Alive', 'Poopybutthole', '', 'Male', NULL, 54, 'https://rickandmortyapi.com/api/character/avatar/252.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (253, 'Numbericon', 'unknown', 'Alien', 'Numbericon', 'unknown', NULL, 43, 'https://rickandmortyapi.com/api/character/avatar/253.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (254, 'Octopus Man', 'Alive', 'Humanoid', 'Octopus-Person', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/254.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (255, 'Orthodox Jew', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/255.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (256, 'Pat Gueterman', 'Dead', 'Robot', '', 'Male', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/256.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (257, 'Paul Fleishman', 'Alive', 'Human', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/257.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (258, 'Pawnshop Clerk', 'Alive', 'Alien', '', 'Male', NULL, 55, 'https://rickandmortyapi.com/api/character/avatar/258.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (259, 'Pencilvester', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/259.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (260, 'Phillip Jacobs', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/260.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (261, 'Photography Cyborg', 'unknown', 'Robot', '', 'Male', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/261.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (262, 'Photography Raptor', 'Dead', 'Alien', 'Parasite', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/262.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (263, 'Pibbles Bodyguard', 'Alive', 'Alien', 'Hairy alien', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/263.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (264, 'Pichael Thompson', 'Alive', 'Humanoid', 'Conjoined twin', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/264.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (265, 'Pickle Rick', 'Alive', 'unknown', 'Pickle', 'Male', 1, 20, 'https://rickandmortyapi.com/api/character/avatar/265.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (266, 'Piece of Toast', 'Alive', 'unknown', 'Bread', 'Genderless', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/266.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (267, 'Plumber Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/267.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (268, 'Poncho', 'Dead', 'Human', '', 'Male', NULL, 5, 'https://rickandmortyapi.com/api/character/avatar/268.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (269, 'Presidentress of The Mega Gargantuans', 'Alive', 'Humanoid', 'Mega Gargantuan', 'Female', 56, 56, 'https://rickandmortyapi.com/api/character/avatar/269.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (270, 'Prince Nebulon', 'Dead', 'Alien', 'Zigerion', 'Male', NULL, 46, 'https://rickandmortyapi.com/api/character/avatar/270.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (271, 'Principal Vagina', 'Alive', 'Cronenberg', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/271.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (272, 'Principal Vagina', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/272.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (273, 'Purge Planet Ruler', 'Dead', 'Alien', 'Cat-Person', 'Male', 9, 9, 'https://rickandmortyapi.com/api/character/avatar/273.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (274, 'Quantum Rick', 'unknown', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/274.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (275, 'Randy Dicknose', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/275.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (276, 'Rat Boss', 'Dead', 'Animal', 'Rat', 'unknown', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/276.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (277, 'Real Fake Doors Salesman', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/277.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (278, 'Regional Manager Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/278.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (279, 'Regular Legs', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/279.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (280, 'Reverse Giraffe', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/280.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (281, 'Reverse Rick Outrage', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/281.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (282, 'Revolio Clockberg Jr.', 'unknown', 'Alien', 'Gear-Person', 'Male', 57, 57, 'https://rickandmortyapi.com/api/character/avatar/282.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (283, 'Rick D. Sanchez III', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/283.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (284, 'Rick Guilt Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/284.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (285, 'Rick Prime', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/285.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (286, 'Rick D-99', 'Dead', 'Human', '', 'Male', 58, 3, 'https://rickandmortyapi.com/api/character/avatar/286.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (287, 'Rick D716', 'Dead', 'Human', '', 'Male', 59, 3, 'https://rickandmortyapi.com/api/character/avatar/287.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (288, 'Rick D716-B', 'Alive', 'Human', '', 'Male', 60, 3, 'https://rickandmortyapi.com/api/character/avatar/288.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (289, 'Rick D716-C', 'Alive', 'Human', '', 'Male', 61, 3, 'https://rickandmortyapi.com/api/character/avatar/289.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (290, 'Rick Sanchez', 'Dead', 'Human', '', 'Male', 34, 34, 'https://rickandmortyapi.com/api/character/avatar/290.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (291, 'Rick J-22', 'Alive', 'Human', '', 'Male', 62, 3, 'https://rickandmortyapi.com/api/character/avatar/291.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (292, 'Rick K-22', 'Alive', 'Human', '', 'Male', 52, 20, 'https://rickandmortyapi.com/api/character/avatar/292.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (293, 'Rick Sanchez', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/293.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (294, 'Ricktiminus Sancheziminius', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/294.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (295, 'Riq IV', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/295.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (296, 'Risotto Groupon', 'Dead', 'Alien', 'Blue ape alien', 'Male', 37, 37, 'https://rickandmortyapi.com/api/character/avatar/296.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (297, 'Risotto''s Tentacled Henchman', 'Dead', 'Alien', 'Tentacle alien', 'Male', 37, 37, 'https://rickandmortyapi.com/api/character/avatar/297.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (298, 'Robot Morty', 'unknown', 'Robot', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/298.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (299, 'Robot Rick', 'unknown', 'Robot', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/299.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (300, 'Roger', 'Dead', 'Human', '', 'Male', 1, 5, 'https://rickandmortyapi.com/api/character/avatar/300.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (301, 'Ron Benson', 'Alive', 'Alien', 'Ring-nippled alien', 'Male', 28, 28, 'https://rickandmortyapi.com/api/character/avatar/301.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (302, 'Ruben', 'Dead', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/302.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (303, 'Samantha', 'Alive', 'Human', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/303.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (304, 'Scary Brandon', 'Alive', 'Mythological Creature', 'Monster', 'Male', 18, 18, 'https://rickandmortyapi.com/api/character/avatar/304.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (305, 'Scary Glenn', 'Alive', 'Mythological Creature', 'Monster', 'Male', 18, 18, 'https://rickandmortyapi.com/api/character/avatar/305.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (306, 'Scary Terry', 'Alive', 'Mythological Creature', 'Monster', 'Male', 18, 18, 'https://rickandmortyapi.com/api/character/avatar/306.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (307, 'Scroopy Noopers', 'Alive', 'Alien', 'Plutonian', 'Male', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/307.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (308, 'Scropon', 'unknown', 'Alien', 'Lobster-Alien', 'Male', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/308.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (309, 'Scrotian', 'Alive', 'Animal', 'Scrotian', 'Male', NULL, 22, 'https://rickandmortyapi.com/api/character/avatar/309.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (310, 'Self-Congratulatory Jerry', 'unknown', 'Mythological Creature', 'Mytholog', 'Male', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/310.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (311, 'Shimshamian', 'Alive', 'Alien', 'Shimshamian', 'Male', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/311.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (312, 'Shlaammi', 'Alive', 'Alien', '', 'unknown', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/312.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (313, 'Shleemypants', 'Alive', 'unknown', 'Omniscient being', 'Male', NULL, 78, 'https://rickandmortyapi.com/api/character/avatar/313.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (314, 'Shmlamantha Shmlicelli', 'Alive', 'Human', '', 'Female', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/314.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (315, 'Shmlangela Shmlobinson-Shmlower', 'Alive', 'Human', '', 'Female', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/315.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (316, 'Shmlona Shmlobinson', 'Alive', 'Human', '', 'Female', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/316.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (317, 'Shmlonathan Shmlower', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/317.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (318, 'Shmlony Shmlicelli', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/318.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (319, 'Shmooglite Runner', 'unknown', 'Alien', 'Animal', 'Male', 37, 37, 'https://rickandmortyapi.com/api/character/avatar/319.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (320, 'Shnoopy Bloopers', 'unknown', 'Alien', '', 'Male', NULL, 7, 'https://rickandmortyapi.com/api/character/avatar/320.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (321, 'Shrimply Pibbles', 'Alive', 'Alien', '', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/321.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (322, 'Simple Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/322.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (323, 'Slaveowner', 'Dead', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/323.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (324, 'Sleepy Gary', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/324.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (325, 'Slick Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/325.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (326, 'Slippery Stair', 'Alive', 'Mythological Creature', 'Slug', 'Male', 48, 20, 'https://rickandmortyapi.com/api/character/avatar/326.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (327, 'Slow Mobius', 'Alive', 'Humanoid', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/327.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (328, 'Slow Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/328.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (329, 'Snuffles (Snowball)', 'Alive', 'Animal', 'Dog', 'Male', 1, NULL, 'https://rickandmortyapi.com/api/character/avatar/329.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (330, 'Solicitor Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/330.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (331, 'Squanchy', 'unknown', 'Alien', 'Cat-Person', 'Male', 35, 35, 'https://rickandmortyapi.com/api/character/avatar/331.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (332, 'Stacy', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/332.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (333, 'Stair Goblin', 'Alive', 'Mythological Creature', 'Stair goblin', 'Genderless', NULL, 48, 'https://rickandmortyapi.com/api/character/avatar/333.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (334, 'Stealy', 'Alive', 'Poopybutthole', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/334.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (335, 'Steve', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/335.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (336, 'Steven Phillips', 'Alive', 'Alien', 'Unknown-nippled alien', 'Male', 28, 28, 'https://rickandmortyapi.com/api/character/avatar/336.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (337, 'Stu', 'Dead', 'Alien', 'Zigerion', 'Male', NULL, 46, 'https://rickandmortyapi.com/api/character/avatar/337.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (338, 'Summer Smith', 'Alive', 'Human', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/338.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (339, 'Summer Smith', 'Alive', 'Human', '', 'Female', 34, 34, 'https://rickandmortyapi.com/api/character/avatar/339.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (340, 'Supernova', 'Alive', 'Human', 'Superhuman', 'Female', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/340.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (341, 'Taddy Mason', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/341.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (342, 'Taint Washer', 'Alive', 'Human', '', 'Male', 8, 8, 'https://rickandmortyapi.com/api/character/avatar/342.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (343, 'Tammy Guetermann', 'Alive', 'Cronenberg', '', 'Female', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/343.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (344, 'Tammy Guetermann', 'Dead', 'Human', '', 'Female', 20, 105, 'https://rickandmortyapi.com/api/character/avatar/344.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (345, 'Teacher Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/345.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (346, 'Terry', 'unknown', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/346.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (347, 'President Curtis', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/347.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (348, 'The President of the Miniverse', 'Dead', 'Humanoid', 'Miniverse inhabitant', 'Male', 49, 49, 'https://rickandmortyapi.com/api/character/avatar/348.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (349, 'The Scientist Formerly Known as Rick', 'Dead', 'Human', '', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/349.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (350, 'Thomas Lipkip', 'unknown', 'Human', '', 'Male', 20, 63, 'https://rickandmortyapi.com/api/character/avatar/350.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (351, 'Three Unknown Things', 'Alive', 'Alien', '', 'unknown', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/351.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (352, 'Tinkles', 'Dead', 'Alien', 'Parasite', 'Female', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/352.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (353, 'Tiny Rick', 'Dead', 'Human', 'Clone', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/353.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (354, 'Toby Matthews', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/354.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (355, 'Todd Crystal', 'Alive', 'Alien', 'Unknown-nippled alien', 'Male', 28, 28, 'https://rickandmortyapi.com/api/character/avatar/355.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (356, 'Tom Randolph', 'Alive', 'Human', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/356.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (357, 'Tommy''s Clone', 'Alive', 'Human', 'Clone', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/357.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (358, 'Tophat Jones', 'Dead', 'Mythological Creature', 'Leprechaun', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/358.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (359, 'Tortured Morty', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/359.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (360, 'Toxic Morty', 'Dead', 'Humanoid', 'Morty''s toxic side', 'Male', 64, 20, 'https://rickandmortyapi.com/api/character/avatar/360.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (361, 'Toxic Rick', 'Dead', 'Humanoid', 'Rick''s toxic side', 'Male', 64, 20, 'https://rickandmortyapi.com/api/character/avatar/361.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (362, 'Traflorkian', 'Alive', 'Alien', 'Traflorkian', 'unknown', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/362.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (363, 'Trandor', 'Alive', 'Alien', 'Krootabulan', 'Male', 45, 20, 'https://rickandmortyapi.com/api/character/avatar/363.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (364, 'Tree Person', 'Dead', 'Humanoid', 'Teenyverse inhabitant', 'unknown', 50, 50, 'https://rickandmortyapi.com/api/character/avatar/364.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (365, 'Tricia Lange', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/365.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (366, 'Trunk Morty', 'Alive', 'Humanoid', 'Trunk-Person', 'Male', 65, 3, 'https://rickandmortyapi.com/api/character/avatar/366.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (367, 'Trunk Man', 'Alive', 'Humanoid', 'Trunk-Person', 'Male', 65, 6, 'https://rickandmortyapi.com/api/character/avatar/367.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (368, 'Truth Tortoise', 'unknown', 'Mythological Creature', 'Omniscient being', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/368.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (369, 'Tusked Assassin', 'unknown', 'Alien', 'Tuskfish', 'Male', 37, 37, 'https://rickandmortyapi.com/api/character/avatar/369.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (370, 'Two Guys with Handlebar Mustaches', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/370.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (371, 'Tumblorkian', 'Alive', 'Alien', 'Tumblorkian', 'Male', 66, 66, 'https://rickandmortyapi.com/api/character/avatar/371.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (372, 'Unity', 'Alive', 'Alien', 'Hivemind', 'Genderless', NULL, 28, 'https://rickandmortyapi.com/api/character/avatar/372.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (373, 'Unmuscular Michael', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/373.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (374, 'Vampire Master', 'Alive', 'Mythological Creature', 'Vampire', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/374.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (375, 'Vance Maximus', 'Dead', 'Human', '', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/375.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (376, 'Veronica Ann Bennet', 'Alive', 'Alien', 'Gazorpian', 'Female', 40, 40, 'https://rickandmortyapi.com/api/character/avatar/376.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (377, 'Voltematron', 'Dead', 'Alien', 'Parasite', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/377.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (378, 'Wall Crawling Rick', 'unknown', 'Humanoid', 'Lizard-Person', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/378.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (379, 'Wedding Bartender', 'unknown', 'Alien', '', 'Male', NULL, 35, 'https://rickandmortyapi.com/api/character/avatar/379.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (380, 'Unknown Rick', 'unknown', 'Human', '', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/380.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (381, 'Woman Rick', 'Alive', 'Alien', 'Chair', 'Female', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/381.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (382, 'Worldender', 'Dead', 'Alien', '', 'Male', NULL, 4, 'https://rickandmortyapi.com/api/character/avatar/382.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (383, 'Yaarb', 'Alive', 'Alien', '', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/383.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (384, 'Yellow Headed Doctor', 'Alive', 'Alien', '', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/384.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (385, 'Yellow Shirt Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/385.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (386, 'Zarbadar Gloonch', 'Dead', 'Alien', 'Drumbloxian', 'Female', NULL, 13, 'https://rickandmortyapi.com/api/character/avatar/386.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (387, 'Zarbadar''s Mytholog', 'unknown', 'Mythological Creature', 'Mytholog', 'Female', 13, 13, 'https://rickandmortyapi.com/api/character/avatar/387.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (388, 'Zeep Xanflorp', 'Alive', 'Humanoid', 'Microverse inhabitant', 'Male', 24, 24, 'https://rickandmortyapi.com/api/character/avatar/388.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (389, 'Zeta Alpha Rick', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/389.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (390, 'Zick Zack', 'Dead', 'Alien', 'Floop Floopian', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/390.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (391, 'Uncle Steve', 'Dead', 'Alien', 'Parasite', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/391.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (392, 'Bearded Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/392.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (393, 'Roy', 'Alive', 'Human', 'Game', 'Male', 32, 32, 'https://rickandmortyapi.com/api/character/avatar/393.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (394, 'Davin', 'Dead', 'Cronenberg', '', 'Male', 1, 1, 'https://rickandmortyapi.com/api/character/avatar/394.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (395, 'Greebybobe', 'Alive', 'Alien', 'Greebybobe', 'unknown', 68, 4, 'https://rickandmortyapi.com/api/character/avatar/395.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (396, 'Scary Teacher', 'Alive', 'Mythological Creature', 'Monster', 'Male', 18, 18, 'https://rickandmortyapi.com/api/character/avatar/396.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (397, 'Fido', 'Alive', 'Animal', 'Dog', 'Male', 70, 70, 'https://rickandmortyapi.com/api/character/avatar/397.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (398, 'Accountant dog', 'Alive', 'Animal', 'Dog', 'Male', 70, 70, 'https://rickandmortyapi.com/api/character/avatar/398.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (399, 'Tiny-persons advocacy group lawyer', 'Alive', 'Mythological Creature', 'Giant', 'Male', 14, 14, 'https://rickandmortyapi.com/api/character/avatar/399.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (400, 'Giant Judge', 'Alive', 'Mythological Creature', 'Giant', 'Male', 14, 14, 'https://rickandmortyapi.com/api/character/avatar/400.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (401, 'Morty Jr''s interviewer', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/401.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (402, 'Guy from The Bachelor', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/402.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (403, 'Corn detective', 'Dead', 'Humanoid', 'Corn-person', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/403.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (404, 'Michael Jackson', 'Alive', 'Humanoid', 'Phone-Person', 'Male', 72, 72, 'https://rickandmortyapi.com/api/character/avatar/404.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (405, 'Trunkphobic suspenders guy', 'Alive', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/405.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (406, 'Spiderweb teddy bear', 'Alive', 'Animal', 'Teddy Bear', 'unknown', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/406.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (407, 'Regular Tyrion Lannister', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/407.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (408, 'Quick Mystery Presenter', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/408.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (409, 'Mr. Sneezy', 'Alive', 'Human', 'Little Human', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/409.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (410, 'Two Brothers', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/410.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (411, 'Alien Mexican Armada', 'unknown', 'Alien', 'Mexican', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/411.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (412, 'Giant Cat Monster', 'unknown', 'Animal', 'Giant Cat Monster', 'unknown', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/412.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (413, 'Old Women', 'unknown', 'Human', 'Old Amazons', 'Female', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/413.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (414, 'Trunkphobic guy', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/414.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (415, 'Pro trunk people marriage guy', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/415.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (416, 'Muscular Mannie', 'Alive', 'Human', 'Mannie', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/416.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (417, 'Baby Legs Chief', 'Alive', 'Human', '', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/417.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (418, 'Mrs. Sullivan''s Boyfriend', 'Alive', 'Human', 'Necrophiliac', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/418.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (419, 'Plutonian Hostess', 'Alive', 'Alien', 'Plutonian', 'Female', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/419.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (420, 'Plutonian Host', 'Alive', 'Alien', 'Plutonian', 'Male', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/420.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (421, 'Rich Plutonian', 'Alive', 'Alien', 'Plutonian', 'Female', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/421.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (422, 'Rich Plutonian', 'Alive', 'Alien', 'Plutonian', 'Male', 47, 47, 'https://rickandmortyapi.com/api/character/avatar/422.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (423, 'Synthetic Laser Eels', 'Alive', 'Animal', 'Eel', 'unknown', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/423.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (424, 'Pizza-person', 'Alive', 'Humanoid', 'Pizza', 'Male', 71, 71, 'https://rickandmortyapi.com/api/character/avatar/424.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (425, 'Pizza-person', 'Alive', 'Humanoid', 'Pizza', 'Male', 71, 71, 'https://rickandmortyapi.com/api/character/avatar/425.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (426, 'Greasy Grandma', 'Alive', 'Human', 'Grandma', 'Female', 73, 73, 'https://rickandmortyapi.com/api/character/avatar/426.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (427, 'Phone-person', 'Alive', 'Humanoid', 'Phone', 'Male', 72, 72, 'https://rickandmortyapi.com/api/character/avatar/427.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (428, 'Phone-person', 'Alive', 'Humanoid', 'Phone', 'Male', 72, 72, 'https://rickandmortyapi.com/api/character/avatar/428.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (429, 'Chair-person', 'Alive', 'Humanoid', 'Chair', 'Male', 74, 74, 'https://rickandmortyapi.com/api/character/avatar/429.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (430, 'Chair-person', 'Alive', 'Humanoid', 'Chair', 'Male', 74, 74, 'https://rickandmortyapi.com/api/character/avatar/430.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (431, 'Chair-homeless', 'Alive', 'Humanoid', 'Chair', 'Male', 74, 74, 'https://rickandmortyapi.com/api/character/avatar/431.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (432, 'Chair-waiter', 'Alive', 'Humanoid', 'Chair', 'Male', 74, 74, 'https://rickandmortyapi.com/api/character/avatar/432.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (433, 'Doopidoo', 'Alive', 'Animal', 'Doopidoo', 'unknown', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/433.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (434, 'Super Weird Rick', 'unknown', 'Human', '', 'Male', NULL, NULL, 'https://rickandmortyapi.com/api/character/avatar/434.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (435, 'Pripudlian', 'Alive', 'Alien', 'Pripudlian', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/435.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (436, 'Giant Testicle Monster', 'Alive', 'Alien', '', 'unknown', 21, 21, 'https://rickandmortyapi.com/api/character/avatar/436.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (437, 'Michael', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/437.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (438, 'Michael''s Lawyer', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/438.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (439, 'Veterinary', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/439.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (440, 'Veterinary Nurse', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/440.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (441, 'Bearded Jerry', 'Alive', 'Human', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/441.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (442, 'Shaved Head Jerry', 'Alive', 'Human', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/442.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (443, 'Tank Top Jerry', 'Alive', 'Human', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/443.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (444, 'Pink Polo Shirt Jerry', 'Alive', 'Human', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/444.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (445, 'Jerryboree Keeper', 'Alive', 'Alien', '', 'Female', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/445.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (446, 'Jerryboree Receptionist', 'Alive', 'Alien', '', 'Male', NULL, 44, 'https://rickandmortyapi.com/api/character/avatar/446.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (447, 'Anchor Gear', 'Alive', 'Alien', 'Gear-Person', 'Male', 57, 57, 'https://rickandmortyapi.com/api/character/avatar/447.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (448, 'Gear Cop', 'Dead', 'Alien', 'Gear-Person', 'Male', 57, 57, 'https://rickandmortyapi.com/api/character/avatar/448.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (449, 'Roy''s Mum', 'Alive', 'Human', 'Game', 'Female', 32, 32, 'https://rickandmortyapi.com/api/character/avatar/449.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (450, 'Roy''s Wife', 'Alive', 'Human', 'Game', 'Male', 32, 32, 'https://rickandmortyapi.com/api/character/avatar/450.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (451, 'Roy''s Son', 'Alive', 'Human', 'Game', 'Male', 32, 32, 'https://rickandmortyapi.com/api/character/avatar/451.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (452, 'Simon', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/452.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (453, 'Vampire Master''s Assistant', 'Alive', 'Mythological Creature', 'Vampire', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/453.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (454, 'Arbolian Mentirososian', 'Alive', 'Alien', '', 'unknown', 75, 16, 'https://rickandmortyapi.com/api/character/avatar/454.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (455, 'St. Gloopy Noops Nurse', 'Alive', 'Alien', '', 'Female', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/455.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (456, 'Nano Doctor', 'Alive', 'Alien', 'Nano Alien', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/456.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (457, 'Funny Songs Presenter', 'Alive', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/457.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (458, 'Tax Attorney', 'unknown', 'Human', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/458.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (459, 'Butthole Ice Cream Guy', 'Alive', 'Alien', '', 'Male', NULL, 6, 'https://rickandmortyapi.com/api/character/avatar/459.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (460, 'Traflorkian Journalist', 'Alive', 'Alien', 'Traflorkian', 'Male', NULL, 16, 'https://rickandmortyapi.com/api/character/avatar/460.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (461, 'Communication''s Responsible Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/461.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (462, 'Teleportation''s Responsible Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/462.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (463, 'SEAL Team Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/463.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (464, 'SEAL Team Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/464.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (465, 'SEAL Team Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/465.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (466, 'SEAL Team Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/466.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (467, 'Morphizer-XE Customer Support', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/467.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (468, 'Morphizer-XE Customer Support', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/468.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (469, 'Morphizer-XE Customer Support', 'unknown', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/469.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (470, 'Alien Spa Employee', 'Alive', 'Alien', '', 'Male', NULL, 76, 'https://rickandmortyapi.com/api/character/avatar/470.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (471, 'Little Voltron', 'Alive', 'Robot', '', 'Genderless', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/471.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (472, 'Baby Rick', 'Alive', 'Human', 'Clone', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/472.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (473, 'Bartender Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/473.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (474, 'Dancer Cowboy Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/474.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (475, 'Dancer Morty', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/475.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (476, 'Flower Morty', 'Alive', 'Human', 'Human with a flower in his head', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/476.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (477, 'Hairdresser Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/477.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (478, 'Journalist Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/478.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (479, 'Private Sector Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/479.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (480, 'Purple Morty', 'Alive', 'Alien', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/480.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (481, 'Retired General Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/481.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (482, 'Secret Service Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/482.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (483, 'Steve Jobs Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/483.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (484, 'Sheik Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/484.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (485, 'Modern Rick', 'Alive', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/485.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (486, 'Tan Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/486.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (487, 'Visor Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/487.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (488, 'Colonial Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/488.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (489, 'P-Coat Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/489.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (490, 'Chang', 'Alive', 'Human', '', 'Male', NULL, 25, 'https://rickandmortyapi.com/api/character/avatar/490.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (491, 'Dr. Eleanor Arroway', 'Alive', 'Human', '', 'Female', NULL, 25, 'https://rickandmortyapi.com/api/character/avatar/491.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (492, 'Varrix', 'Alive', 'Alien', '', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/492.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (493, 'Secretary of the Interior', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/493.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (494, 'Crystal Poacher', 'Dead', 'Alien', '', 'Male', NULL, 79, 'https://rickandmortyapi.com/api/character/avatar/494.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (495, 'Crystal Poacher', 'Dead', 'Alien', '', 'Male', NULL, 79, 'https://rickandmortyapi.com/api/character/avatar/495.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (496, 'Crystal Poacher', 'Dead', 'Alien', '', 'Male', NULL, 79, 'https://rickandmortyapi.com/api/character/avatar/496.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (497, 'Hologram Rick', 'Dead', 'Humanoid', 'Hologram', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/497.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (498, 'Fascist Rick', 'Dead', 'Human', '', 'Male', 77, 77, 'https://rickandmortyapi.com/api/character/avatar/498.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (499, 'Fascist Morty', 'Dead', 'Human', '', 'Male', 77, 77, 'https://rickandmortyapi.com/api/character/avatar/499.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (500, 'Fascist Mr. President', 'Alive', 'Human', '', 'Male', 77, 77, 'https://rickandmortyapi.com/api/character/avatar/500.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (501, 'Fascist Rick’s Clone', 'Dead', 'Human', 'Clone', 'Male', 77, 77, 'https://rickandmortyapi.com/api/character/avatar/501.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (502, 'Revolio Clockberg Jr.', 'Dead', 'Alien', 'Gear-Person', 'Male', 77, 77, 'https://rickandmortyapi.com/api/character/avatar/502.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (503, 'Fascist Shrimp Rick', 'Alive', 'Animal', 'Shrimp', 'Male', 80, 80, 'https://rickandmortyapi.com/api/character/avatar/503.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (504, 'Fascist Shrimp Rick’s Clone', 'Dead', 'Animal', 'Shrimp', 'Male', 80, 80, 'https://rickandmortyapi.com/api/character/avatar/504.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (505, 'Fascist Shrimp Morty', 'Alive', 'Animal', 'Shrimp', 'Male', 80, 80, 'https://rickandmortyapi.com/api/character/avatar/505.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (506, 'Fascist Shrimp SS', 'Alive', 'Animal', 'Shrimp', 'Male', 80, 80, 'https://rickandmortyapi.com/api/character/avatar/506.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (507, 'Fascist Teddy Bear Rick', 'Alive', 'Animal', 'Teddy Bear', 'Male', 81, 81, 'https://rickandmortyapi.com/api/character/avatar/507.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (508, 'Fascist Teddy Bear Rick’s Clone', 'Dead', 'Animal', 'Teddy Bear', 'Male', 81, 81, 'https://rickandmortyapi.com/api/character/avatar/508.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (509, 'Bully', 'unknown', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/509.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (510, 'Anchorman', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/510.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (511, 'Anchorwoman', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/511.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (512, 'Morty’s Lawyer', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/512.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (513, 'Judge', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/513.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (514, 'Public Opinion Judge', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/514.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (515, 'Caterpillar Mr. Goldenfold', 'Dead', 'Animal', 'Caterpillar', 'Male', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/515.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (516, 'Wasp Rick', 'Alive', 'Animal', 'Wasp', 'Male', 82, 20, 'https://rickandmortyapi.com/api/character/avatar/516.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (517, 'Wasp Rick’s Clone', 'unknown', 'Animal', 'Wasp', 'Male', 82, 20, 'https://rickandmortyapi.com/api/character/avatar/517.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (518, 'Wasp Morty', 'Alive', 'Animal', 'Wasp', 'Male', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/518.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (519, 'Wasp Summer', 'Alive', 'Animal', 'Wasp', 'Female', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/519.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (520, 'Wasp Jerry', 'Alive', 'Animal', 'Wasp', 'Male', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/520.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (521, 'Wasp Beth', 'Alive', 'Animal', 'Wasp', 'Female', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/521.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (522, 'Caterpillar Mr. Goldenfold’s Larvae', 'Dead', 'Animal', 'Caterpillar', 'unknown', 82, 82, 'https://rickandmortyapi.com/api/character/avatar/522.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (523, 'Boglin', 'unknown', 'unknown', 'Toy', 'unknown', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/523.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (524, 'Kirkland Brand Mr. Meeseeks', 'unknown', 'Humanoid', 'Meeseeks', 'Male', 53, 20, 'https://rickandmortyapi.com/api/character/avatar/524.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (525, 'Glootie', 'Alive', 'Alien', 'Monogatron', 'Male', NULL, 83, 'https://rickandmortyapi.com/api/character/avatar/525.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (526, 'Danny Publitz', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/526.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (527, 'Mothership Intern', 'Alive', 'Alien', 'Monogatron', 'Male', NULL, 83, 'https://rickandmortyapi.com/api/character/avatar/527.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (528, 'Monogatron Leader', 'Alive', 'Alien', 'Monogatron', 'Male', NULL, 83, 'https://rickandmortyapi.com/api/character/avatar/528.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (529, 'Lizard', 'Dead', 'Alien', 'Lizard', 'Male', 85, 85, 'https://rickandmortyapi.com/api/character/avatar/529.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (530, 'Deliverance', 'Dead', 'Robot', '', 'Genderless', 85, 85, 'https://rickandmortyapi.com/api/character/avatar/530.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (531, 'Tony', 'Dead', 'Alien', '', 'Male', NULL, 86, 'https://rickandmortyapi.com/api/character/avatar/531.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (532, 'Tony’s Wife', 'Dead', 'Alien', '', 'Female', 87, 87, 'https://rickandmortyapi.com/api/character/avatar/532.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (533, 'Monogatron Queen', 'Alive', 'Alien', 'Monogatron', 'Female', NULL, 83, 'https://rickandmortyapi.com/api/character/avatar/533.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (534, 'Tony''s Dad', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/534.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (535, 'Jeff', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/535.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (536, 'Josiah', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/536.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (537, 'Maggie', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/537.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (538, 'Priest Witherspoon', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/538.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (539, 'Richard', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/539.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (540, 'Running Bird', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/540.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (541, 'Secretary at Tony''s', 'Alive', 'Alien', '', 'Female', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/541.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (542, 'Mountain Sweat Jerry', 'Alive', 'Human', '', 'Male', 87, 87, 'https://rickandmortyapi.com/api/character/avatar/542.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (543, 'Vermigurber', 'Alive', 'Alien', 'Fly', 'Male', NULL, 84, 'https://rickandmortyapi.com/api/character/avatar/543.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (544, 'Miles Knightly', 'Dead', 'Alien', '', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/544.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (545, 'Heist-Con Receptionist', 'Alive', 'Alien', '', 'Female', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/545.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (546, 'Angie Flint', 'Alive', 'Human', 'Cyborg', 'Female', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/546.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (547, 'Glar', 'Alive', 'Alien', '', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/547.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (548, 'Truckula', 'Alive', 'Mythological Creature', 'Vampire', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/548.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (549, 'Snake Arms', 'Alive', 'Alien', '', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/549.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (550, 'Double Microwawe', 'Alive', 'Humanoid', 'Cyborg', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/550.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (551, 'Monitor Lord', 'Alive', 'Alien', '', 'Male', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/551.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (552, 'Key Catcher', 'Alive', 'Alien', '', 'Female', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/552.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (553, 'The Shapeshiftress', 'Alive', 'Alien', 'Shapeshifter', 'Female', NULL, 88, 'https://rickandmortyapi.com/api/character/avatar/553.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (554, 'Heistotron', 'Dead', 'Robot', '', 'Genderless', 20, 89, 'https://rickandmortyapi.com/api/character/avatar/554.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (555, 'Randotron', 'Dead', 'Robot', '', 'Genderless', 20, 89, 'https://rickandmortyapi.com/api/character/avatar/555.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (556, 'Hephaestus', 'Alive', 'Mythological Creature', 'God', 'Male', 90, 89, 'https://rickandmortyapi.com/api/character/avatar/556.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (557, 'Ventriloquiver', 'Alive', 'Humanoid', 'Dummy', 'Female', 91, 89, 'https://rickandmortyapi.com/api/character/avatar/557.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (558, 'Elon Tusk', 'Alive', 'Human', 'Human with tusks', 'Male', 92, 89, 'https://rickandmortyapi.com/api/character/avatar/558.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (559, 'Gramuflackian Anchorman', 'Dead', 'Alien', 'Gramuflackian', 'Male', 93, 93, 'https://rickandmortyapi.com/api/character/avatar/559.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (560, 'Gramuflackian General', 'Dead', 'Alien', 'Gramuflackian', 'Male', 93, 93, 'https://rickandmortyapi.com/api/character/avatar/560.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (561, 'Netflix Executive', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/561.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (562, 'Balthromaw', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 20, 'https://rickandmortyapi.com/api/character/avatar/562.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (563, 'The Wizard', 'Dead', 'Human', '', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/563.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (564, 'Talking Cat', 'Alive', 'Animal', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/564.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (565, 'Debrah', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/565.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (566, 'Debrah’s Partner', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/566.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (567, 'Michael', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/567.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (568, 'Slut Dragon', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/568.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (569, 'Shadow Jacker', 'Alive', 'Mythological Creature', 'Dragon', 'Male', 94, 94, 'https://rickandmortyapi.com/api/character/avatar/569.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (570, 'Chachi', 'Dead', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/570.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (571, 'Slippy', 'Alive', 'Animal', 'Snake', 'Female', 20, 78, 'https://rickandmortyapi.com/api/character/avatar/571.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (572, 'Robot Snake', 'unknown', 'Robot', 'Snake', 'Genderless', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/572.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (573, 'Snake Hitler', 'Dead', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/573.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (574, 'Snake Lincoln', 'Dead', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/574.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (575, 'Snake Resistance Robot', 'Dead', 'Robot', 'Human-Snake hybrid', 'Genderless', 78, 20, 'https://rickandmortyapi.com/api/character/avatar/575.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (576, 'Snake Linguist', 'unknown', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/576.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (577, 'Snake Terminator', 'unknown', 'Robot', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/577.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (578, 'Snake Soldier', 'Alive', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/578.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (579, 'Snake with Legs', 'Alive', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/579.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (580, 'Secret Service Snake', 'Alive', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/580.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (581, 'Anchosnake', 'Alive', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/581.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (582, 'Anchosnake', 'Alive', 'Animal', 'Snake', 'Female', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/582.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (583, '80''s snake', 'unknown', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/583.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (584, 'Bar Customer', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/584.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (585, 'Bartender', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/585.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (586, 'PC Basketball Player', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/586.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (587, 'Cavesnake', 'Dead', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/587.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (588, 'Pet Shop Employee', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/588.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (589, 'Snake Reporter', 'Alive', 'Animal', 'Snake', 'Male', 78, 78, 'https://rickandmortyapi.com/api/character/avatar/589.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (590, 'High Pilot', 'unknown', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/590.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (591, 'High Pilot', 'unknown', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/591.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (592, 'Phoenixperson', 'Dead', 'Alien', 'Cyborg', 'Male', 15, 20, 'https://rickandmortyapi.com/api/character/avatar/592.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (593, 'Tickets Please Guy', 'Dead', 'Human', 'Soulless Puppet', 'Male', 96, 97, 'https://rickandmortyapi.com/api/character/avatar/593.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (594, 'Floaty Bloody Man', 'Dead', 'Human', 'Half Soulless Puppet', 'Male', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/594.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (595, 'Floaty Non-Gasm Brotherhood Member', 'Dead', 'Alien', 'Soulless Puppet', 'Male', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/595.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (596, 'Floaty Non-Gasm Brotherhood Member Friend', 'Dead', 'Alien', 'Soulless Puppet', 'Female', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/596.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (597, 'Abradolf Lincler', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/597.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (598, 'Biblesaurus', 'Alive', 'Animal', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/598.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (599, 'Birdperson', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/599.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (600, 'Cats Fan', 'Alive', 'Alien', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/600.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (601, 'Christmas Storyteller', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/601.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (602, 'Cookies Guy', 'Alive', 'Human', 'Soulless Puppet', 'Male', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/602.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (603, 'Crossy', 'Alive', 'unknown', 'Soulless Puppet', 'Genderless', 100, 100, 'https://rickandmortyapi.com/api/character/avatar/603.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (604, 'Female Scorpion', 'Dead', 'Animal', 'Soulless Puppet', 'Female', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/604.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (605, 'Floaty Bloody Man’s Daughter', 'Dead', 'Human', 'Soulless Puppet', 'Female', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/605.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (606, 'Goomby', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/606.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (607, 'Hairspray Fan', 'Alive', 'Alien', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/607.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (608, 'Jesus Christ', 'Alive', 'Human', 'Soulless Puppet', 'Male', 100, 100, 'https://rickandmortyapi.com/api/character/avatar/608.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (609, 'Josh', 'Dead', 'Human', 'Soulless Puppet', 'Male', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/609.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (610, 'Josh''s Sister', 'Dead', 'Human', 'Soulless Puppet', 'Female', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/610.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (611, 'Leah', 'Alive', 'unknown', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/611.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (612, 'Marcus', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/612.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (613, 'Mike Johnson', 'Alive', 'Human', 'Soulless Puppet', 'Male', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/613.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (614, 'Mr. Celery & Friends', 'Alive', 'unknown', 'Soulless Puppet', 'Genderless', 100, 100, 'https://rickandmortyapi.com/api/character/avatar/614.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (615, 'Musical Fan', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/615.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (616, 'Phantom of the Opera Fan', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/616.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (617, 'Phoenixperson', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/617.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (618, 'Private Smith', 'unknown', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/618.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (619, 'Professor Sanchez', 'Dead', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/619.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (620, 'Ramamama Lord', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/620.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (621, 'Ruth Bader Ginsburg', 'Alive', 'Human', 'Soulless Puppet', 'Female', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/621.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (622, 'Sarge', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/622.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (623, 'Shrek The Musical Fan', 'Alive', 'Alien', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/623.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (624, 'Snuffles', 'Alive', 'Animal', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/624.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (625, 'Storylord', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 100, 'https://rickandmortyapi.com/api/character/avatar/625.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (626, 'Tammy Guetermann', 'Alive', 'Human', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/626.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (627, 'The Concept of Time', 'Alive', 'unknown', 'Soulless Puppet', 'Genderless', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/627.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (628, 'Beth Smith', 'Alive', 'Human', 'Soulless Puppet', 'Female', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/628.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (629, 'Summer Smith', 'Alive', 'Human', 'Soulless Puppet', 'Female', 99, 99, 'https://rickandmortyapi.com/api/character/avatar/629.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (630, 'Morty Smith', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/630.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (631, 'Rick Sanchez', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/631.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (632, 'Train Cop', 'Dead', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/632.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (633, 'Train Cops', 'Alive', 'Human', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/633.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (634, 'Train Cops Instructor', 'Dead', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/634.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (635, 'Darth Poopybutthole', 'Alive', 'Poopybutthole', 'Soulless Puppet', 'Male', 100, 100, 'https://rickandmortyapi.com/api/character/avatar/635.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (636, 'Evil Morty', 'Alive', 'Human', 'Soulless Puppet', 'Male', 100, 100, 'https://rickandmortyapi.com/api/character/avatar/636.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (637, 'Morty’s Disguise', 'Alive', 'Human', 'Soulless Puppet', 'Female', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/637.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (638, 'Rick’s Disguise', 'Alive', 'Human', 'Soulless Puppet', 'Male', 96, 96, 'https://rickandmortyapi.com/api/character/avatar/638.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (639, 'Uncle Nibbles', 'Dead', 'Alien', 'Soulless Puppet', 'Male', 98, 98, 'https://rickandmortyapi.com/api/character/avatar/639.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (640, 'Angry Glorzo', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/640.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (641, 'Bruce', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/641.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (642, 'Council of Glorzos Member', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/642.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (643, 'Council of Glorzos Member', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/643.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (644, 'Old Glorzo', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/644.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (645, 'Shane', 'unknown', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/645.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (646, 'Steve', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/646.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (647, 'Troy', 'Dead', 'Alien', 'Glorzo', 'Male', 101, 101, 'https://rickandmortyapi.com/api/character/avatar/647.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (648, 'Crystal Dealers Boss', 'Dead', 'Alien', '', 'Male', NULL, 102, 'https://rickandmortyapi.com/api/character/avatar/648.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (649, 'Crystal Dealer', 'Dead', 'Alien', '', 'Male', NULL, 102, 'https://rickandmortyapi.com/api/character/avatar/649.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (650, 'Crystal Dealer', 'Dead', 'Alien', '', 'Male', NULL, 102, 'https://rickandmortyapi.com/api/character/avatar/650.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (651, 'Crystal Dealer', 'Dead', 'Alien', '', 'Male', NULL, 102, 'https://rickandmortyapi.com/api/character/avatar/651.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (652, 'SWAT Officer', 'Dead', 'Human', '', 'Male', 103, 103, 'https://rickandmortyapi.com/api/character/avatar/652.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (653, 'Plane Crash Survivor', 'unknown', 'Human', '', 'Female', 104, 104, 'https://rickandmortyapi.com/api/character/avatar/653.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (654, 'Plane Crash Survivor', 'unknown', 'Human', '', 'Male', 104, 104, 'https://rickandmortyapi.com/api/character/avatar/654.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (655, 'Heroine Keith', 'Alive', 'Human', '', 'Male', 104, 103, 'https://rickandmortyapi.com/api/character/avatar/655.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (656, 'Impervious to Acid SWAT Officer', 'Dead', 'Human', '', 'Male', 103, 103, 'https://rickandmortyapi.com/api/character/avatar/656.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (657, 'Johnny Carson', 'Alive', 'Human', '', 'Male', 103, 103, 'https://rickandmortyapi.com/api/character/avatar/657.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (658, 'Sonia Sotomayor', 'Alive', 'Human', '', 'Female', 103, 103, 'https://rickandmortyapi.com/api/character/avatar/658.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (659, 'Morty’s Father-in-law', 'Alive', 'Human', '', 'Male', 104, 104, 'https://rickandmortyapi.com/api/character/avatar/659.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (660, 'Morty’s Mother-in-law', 'Alive', 'Human', '', 'Female', 104, 104, 'https://rickandmortyapi.com/api/character/avatar/660.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (661, 'Morty’s Girlfriend', 'Alive', 'Human', '', 'Female', 104, 103, 'https://rickandmortyapi.com/api/character/avatar/661.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (662, 'Gaia', 'Alive', 'unknown', 'Planet', 'Female', 106, 106, 'https://rickandmortyapi.com/api/character/avatar/662.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (663, 'Reggie', 'Dead', 'Mythological Creature', 'Zeus', 'Male', 90, 106, 'https://rickandmortyapi.com/api/character/avatar/663.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (664, 'Ticktock', 'unknown', 'Humanoid', 'Clay-Person', 'Genderless', 106, 106, 'https://rickandmortyapi.com/api/character/avatar/664.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (665, 'Florflock', 'Alive', 'Humanoid', 'Clay-Person', 'Genderless', 106, 106, 'https://rickandmortyapi.com/api/character/avatar/665.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (666, 'Squeeb', 'Alive', 'Humanoid', 'Clay-Person', 'Genderless', 106, 106, 'https://rickandmortyapi.com/api/character/avatar/666.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (667, 'Defiance Beth', 'Alive', 'Human', 'Clone', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/667.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (668, 'Defiance Squanchette', 'Alive', 'Alien', 'Cat-Person', 'Female', 35, 107, 'https://rickandmortyapi.com/api/character/avatar/668.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (669, 'Defiance Doctor', 'Alive', 'Alien', '', 'Male', NULL, 108, 'https://rickandmortyapi.com/api/character/avatar/669.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (670, 'New Improved Galactic Federation Guard', 'Dead', 'Alien', 'Gromflomite', 'Male', 19, 105, 'https://rickandmortyapi.com/api/character/avatar/670.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (671, 'New Improved Galactic Federation Guard', 'Dead', 'Alien', 'Gromflomite', 'Male', 19, 105, 'https://rickandmortyapi.com/api/character/avatar/671.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (672, 'Mr. Nimbus', 'Alive', 'Mythological Creature', 'Sexy Aquaman', 'Male', 109, 20, 'https://rickandmortyapi.com/api/character/avatar/672.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (673, 'Hoovy', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/673.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (674, 'Bova', 'Dead', 'Humanoid', 'Narnian', 'Female', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/674.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (675, 'Japheth', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/675.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (676, 'Japheth''s Middle Son', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/676.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (677, 'Japheth''s Eldest Son', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/677.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (678, 'Japheth''s Youngest Son', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/678.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (679, 'Japheth''s Grandson', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/679.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (680, 'Adam', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 20, 'https://rickandmortyapi.com/api/character/avatar/680.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (681, 'Adam''s Mother', 'Dead', 'Humanoid', 'Narnian', 'Female', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/681.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (682, 'Warlock', 'Dead', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/682.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (683, 'Evolved Narnian', 'Alive', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/683.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (684, 'Mr. Nimbus Secretary', 'Alive', 'Animal', 'Starfish', 'Female', 109, 20, 'https://rickandmortyapi.com/api/character/avatar/684.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (685, 'Evolved Narnian Disguised as Morty', 'Alive', 'Humanoid', 'Narnian', 'Male', 110, 110, 'https://rickandmortyapi.com/api/character/avatar/685.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (686, 'Mr. Nimbus'' Squid', 'Dead', 'Animal', 'Squid', 'Male', 109, 20, 'https://rickandmortyapi.com/api/character/avatar/686.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (687, 'Scarecrow Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/687.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (688, 'Scarecrow Summer', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/688.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (689, 'Scarecrow Jerry', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/689.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (690, 'Scarecrow Morty', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/690.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (691, 'Scarecrow Beth', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/691.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (692, 'Glockenspiel Jerry', 'Alive', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/692.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (693, 'Glockenspiel Beth', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/693.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (694, 'Glockenspiel Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/694.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (695, 'Glockenspiel Summer', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/695.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (696, 'Glockenspiel Morty', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/696.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (697, 'Wicker Beth', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/697.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (698, 'Wicker Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/698.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (699, 'Wicker Morty', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/699.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (700, 'Wicker Summer', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/700.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (701, 'Metal Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/701.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (702, 'Gun Brain Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/702.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (703, 'Mr. Always Wants to be Hunted', 'Alive', 'Poopybutthole', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/703.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (704, 'Squid Costume Beth', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/704.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (705, 'Squid Costume Jerry', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/705.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (706, 'Squid Costume Morty', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/706.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (707, 'Squid Costume Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/707.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (708, 'Squid Costume Summer', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/708.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (709, 'Dracula', 'Alive', 'Mythological Creature', 'Vampire', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/709.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (710, 'Steve', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/710.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (711, 'When Wolf', 'Alive', 'Mythological Creature', 'Whenwolf', 'Male', 6, 6, 'https://rickandmortyapi.com/api/character/avatar/711.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (712, 'Too Cute to Murder Beth', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/712.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (713, 'Too Cute to Murder Rick', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/713.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (714, 'Too Cute to Murder Jerry', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/714.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (715, 'Too Cute to Murder Morty', 'Dead', 'Robot', 'Decoy', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/715.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (716, 'Too Cute to Murder Summer', 'Dead', 'Robot', 'Decoy', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/716.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (717, 'Planetina', 'Alive', 'Humanoid', 'Summon', 'Female', 111, 20, 'https://rickandmortyapi.com/api/character/avatar/717.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (718, 'Daphne', 'Alive', 'Alien', 'Morglutzian', 'Female', 112, 113, 'https://rickandmortyapi.com/api/character/avatar/718.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (719, 'Diesel Weasel', 'Alive', 'Animal', 'Weasel', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/719.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (720, 'Eddie', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/720.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (721, 'Xing Ho', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/721.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (722, 'Air Tina-Teer', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/722.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (723, 'Water Tina-Teer', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/723.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (724, 'Planetina Buyer', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/724.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (725, 'Tony Galopagus', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/725.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (726, 'Sticky', 'Alive', 'unknown', 'Super Sperm Monster', 'Male', 114, 20, 'https://rickandmortyapi.com/api/character/avatar/726.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (727, 'Professor Shabooboo', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/727.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (728, 'Sperm Queen', 'Dead', 'unknown', 'Super Sperm Monster', 'Female', 114, 20, 'https://rickandmortyapi.com/api/character/avatar/728.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (729, 'CHUD King', 'Alive', 'Animal', 'CHUD', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/729.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (730, 'Princess Ponietta', 'Alive', 'Animal', 'CHUD', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/730.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (731, 'Naruto Smith', 'Alive', 'Humanoid', 'Giant Incest Baby', 'Male', 20, 115, 'https://rickandmortyapi.com/api/character/avatar/731.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (732, 'Blazen', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/732.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (733, 'Kathy Ireland', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/733.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (734, 'Amazing Johnathan', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/734.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (735, 'Foal Sanchez', 'Alive', 'Humanoid', 'CHUD Human Mix', 'unknown', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/735.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (736, 'Spaceman', 'Dead', 'Human', '', 'Male', 20, 115, 'https://rickandmortyapi.com/api/character/avatar/736.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (737, 'Cirque du Soleil Zumanity Member', 'Alive', 'Human', '', 'Female', 104, 20, 'https://rickandmortyapi.com/api/character/avatar/737.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (738, 'Cirque du Soleil Zumanity Member', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/738.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (739, 'Cirque du Soleil Zumanity Member', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/739.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (740, 'Cirque du Soleil Zumanity Member', 'Dead', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/740.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (741, 'Cirque du Soleil Zumanity Member', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/741.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (742, 'Bruce Chutback', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/742.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (743, 'Alyson Hannigan', 'Dead', 'Alien', '', 'Female', NULL, 117, 'https://rickandmortyapi.com/api/character/avatar/743.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (744, 'Cenobite', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/744.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (745, 'Cenobite', 'unknown', 'Mythological Creature', 'Demon', 'Female', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/745.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (746, 'Cenobite', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/746.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (747, 'Cenobite', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/747.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (748, 'Cenobite', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/748.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (749, 'Coat Rack Head', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/749.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (750, 'Mousetrap Nipples', 'unknown', 'Mythological Creature', 'Demon', 'Male', 116, 116, 'https://rickandmortyapi.com/api/character/avatar/750.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (751, 'Changeformer', 'Dead', 'Robot', 'Changeformer', 'Male', 118, 118, 'https://rickandmortyapi.com/api/character/avatar/751.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (752, 'Changeformer', 'Dead', 'Robot', 'Changeformer', 'Male', 118, 118, 'https://rickandmortyapi.com/api/character/avatar/752.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (753, 'Space Cruiser', 'Alive', 'Robot', 'Artificial Intelligence', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/753.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (754, 'Coop', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/754.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (755, 'Dwayne', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/755.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (756, 'Franklin D. Roosevelt', 'Dead', 'Humanoid', 'Guinea Pig for the Polio Vaccine', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/756.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (757, 'President''s General', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/757.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (758, 'Giant Assassin Hidden in the Statue of Liberty', 'Alive', 'Robot', '', 'Female', 119, 20, 'https://rickandmortyapi.com/api/character/avatar/758.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (759, 'Turkey Morty', 'Alive', 'Animal', 'Turkey', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/759.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (760, 'Turkey Rick', 'Alive', 'Animal', 'Turkey', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/760.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (761, 'Turkey President Curtis', 'Alive', 'Animal', 'Turkey', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/761.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (762, 'Martínez', 'Dead', 'Animal', 'Turkey', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/762.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (763, 'Marvin', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/763.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (764, 'Jackey', 'Alive', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/764.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (765, 'Native Alien', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/765.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (766, 'Pilgrim Alien', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/766.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (767, 'President Turkey', 'Dead', 'Humanoid', 'Turkey Human Mix', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/767.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (768, 'Mary-Lou', 'Alive', 'Human', '', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/768.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (769, 'Big Fat rick', 'unknown', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/769.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (770, 'Hothead Rick', 'Dead', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/770.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (771, 'Ricardo Montoya', 'unknown', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/771.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (772, 'Wrap-it-up Little Rick', 'Dead', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/772.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (773, 'Yo-yo Rick', 'unknown', 'Human', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/773.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (774, 'Voiceoverian', 'Dead', 'Alien', 'Parasite', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/774.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (775, 'Voiceoverian', 'Dead', 'Alien', 'Parasite', 'unknown', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/775.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (776, 'Gotron Pilot', 'Dead', 'Human', 'Anime', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/776.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (777, 'Gotron Pilot', 'Dead', 'Human', 'Anime', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/777.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (778, 'Gotron Pilot', 'Dead', 'Human', 'Anime', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/778.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (779, 'Young Memory Rick', 'Alive', 'Human', 'Memory', 'Male', 120, 126, 'https://rickandmortyapi.com/api/character/avatar/779.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (780, 'Memory Tammy', 'Dead', 'Human', 'Memory', 'Female', 120, 120, 'https://rickandmortyapi.com/api/character/avatar/780.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (781, 'Rick''s Garage', 'Alive', 'Robot', 'Artificial Intelligence', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/781.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (782, 'Memory Squanchy', 'Dead', 'Alien', 'Memory', 'Male', 120, 120, 'https://rickandmortyapi.com/api/character/avatar/782.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (783, 'Memory Rick', 'Dead', 'Human', 'Memory', 'Male', 120, 120, 'https://rickandmortyapi.com/api/character/avatar/783.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (784, 'Memory Rick', 'Dead', 'Human', 'Memory', 'Male', 120, 120, 'https://rickandmortyapi.com/api/character/avatar/784.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (785, 'Memory Geardude', 'Dead', 'Alien', 'Memory', 'Male', 120, 120, 'https://rickandmortyapi.com/api/character/avatar/785.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (786, 'Birdperson & Tammy''s Child', 'Alive', 'Humanoid', 'Bird-Person Human Mix', 'Female', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/786.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (787, 'Two Crows', 'Alive', 'Animal', 'Crow', 'unknown', 20, 125, 'https://rickandmortyapi.com/api/character/avatar/787.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (788, 'Mr. Cookie President', 'Alive', 'Alien', 'Cookie', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/788.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (789, 'Nick', 'Dead', 'Human', '', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/789.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (790, 'Harold (Garbage Goober)', 'Alive', 'Alien', '', 'Male', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/790.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (791, 'Harold''s Wife', 'Alive', 'Alien', '', 'Female', NULL, 20, 'https://rickandmortyapi.com/api/character/avatar/791.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (792, 'Alien Crow', 'Dead', 'Alien', 'Crow', 'Male', 122, 122, 'https://rickandmortyapi.com/api/character/avatar/792.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (793, 'Alien Crow', 'Dead', 'Alien', 'Crow', 'Female', 122, 122, 'https://rickandmortyapi.com/api/character/avatar/793.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (794, 'Samansky', 'Dead', 'Alien', 'Normal Size Bug', 'Male', 123, 20, 'https://rickandmortyapi.com/api/character/avatar/794.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (795, 'Palicki', 'Alive', 'Alien', 'Normal Size Bug', 'Male', 123, 123, 'https://rickandmortyapi.com/api/character/avatar/795.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (796, 'Sarge', 'Alive', 'Alien', 'Normal Size Bug', 'Male', 123, 123, 'https://rickandmortyapi.com/api/character/avatar/796.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (797, 'Slartivartian', 'Dead', 'Alien', 'Slartivartian', 'Male', 124, 124, 'https://rickandmortyapi.com/api/character/avatar/797.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (798, 'Ferkusian', 'Alive', 'Alien', 'Ferkusian', 'Male', 113, 113, 'https://rickandmortyapi.com/api/character/avatar/798.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (799, 'Morglutzian', 'Dead', 'Alien', 'Morglutzian', 'Male', 112, 112, 'https://rickandmortyapi.com/api/character/avatar/799.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (800, 'Super Turkey', 'Dead', 'Humanoid', 'Turkey Human Mix', 'Male', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/800.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (801, '7+7 Years Old Morty', 'unknown', 'Human', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/801.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (802, '26 Years Old Morty', 'Dead', 'Human', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/802.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (803, '40 Years Old Morty', 'unknown', 'Human', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/803.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (804, 'Andy', 'unknown', 'Human', 'Mascot', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/804.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (805, 'Baby Mouse Skin Morty', 'Alive', 'Human', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/805.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (806, 'Metaphor for Capitalism', 'Dead', 'Humanoid', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/806.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (807, 'Beth Sanchez', 'Dead', 'Human', '', 'Female', 1, 126, 'https://rickandmortyapi.com/api/character/avatar/807.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (808, 'Crow Scare', 'Alive', 'Humanoid', 'Scarecrow', 'Male', 125, 125, 'https://rickandmortyapi.com/api/character/avatar/808.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (809, 'Pussifer', 'Dead', 'Animal', 'Tiger', 'Male', 125, 125, 'https://rickandmortyapi.com/api/character/avatar/809.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (810, 'Stan Lee Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/810.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (811, 'Re-Build-a-Morty Morty', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/811.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (812, 'Deformed Morty', 'Dead', 'Humanoid', '', 'Male', 3, 3, 'https://rickandmortyapi.com/api/character/avatar/812.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (813, 'Crow Horse', 'Alive', 'Robot', 'Crow Horse', 'unknown', 125, 20, 'https://rickandmortyapi.com/api/character/avatar/813.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (814, 'Bald Rick', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/814.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (815, 'Punk Rick', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/815.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (816, 'Party Rick', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/816.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (817, 'Scar Rick', 'Dead', 'Human', '', 'Male', NULL, 126, 'https://rickandmortyapi.com/api/character/avatar/817.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (818, 'Long Hair Rick', 'unknown', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/818.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (819, 'Redhead Rick', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/819.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (820, 'Redhead Morty', 'Dead', 'Human', '', 'Male', NULL, 3, 'https://rickandmortyapi.com/api/character/avatar/820.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (821, 'Gotron', 'unknown', 'Robot', 'Ferret Robot', 'Genderless', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/821.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (822, 'Young Jerry', 'unknown', 'Human', '', 'Male', 30, 30, 'https://rickandmortyapi.com/api/character/avatar/822.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (823, 'Young Beth', 'unknown', 'Human', '', 'Female', 30, 30, 'https://rickandmortyapi.com/api/character/avatar/823.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (824, 'Young Beth', 'unknown', 'Human', '', 'Female', 30, 30, 'https://rickandmortyapi.com/api/character/avatar/824.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (825, 'Young Jerry', 'unknown', 'Human', '', 'Male', 30, 30, 'https://rickandmortyapi.com/api/character/avatar/825.jpeg');
INSERT INTO characters (id, name, status, species, type, gender, origin_id, location_id, image) VALUES (826, 'Butter Robot', 'Alive', 'Robot', 'Passing Butter Robot', 'Genderless', 20, 20, 'https://rickandmortyapi.com/api/character/avatar/826.jpeg');
INSERT INTO episodes (id, name, air_date, episode) VALUES (1, 'Pilot', 'December 2, 2013', 'S01E01');
INSERT INTO episodes (id, name, air_date, episode) VALUES (2, 'Lawnmower Dog', 'December 9, 2013', 'S01E02');
INSERT INTO episodes (id, name, air_date, episode) VALUES (3, 'Anatomy Park', 'December 16, 2013', 'S01E03');
INSERT INTO episodes (id, name, air_date, episode) VALUES (4, 'M. Night Shaym-Aliens!', 'January 13, 2014', 'S01E04');
INSERT INTO episodes (id, name, air_date, episode) VALUES (5, 'Meeseeks and Destroy', 'January 20, 2014', 'S01E05');
INSERT INTO episodes (id, name, air_date, episode) VALUES (6, 'Rick Potion #9', 'January 27, 2014', 'S01E06');
INSERT INTO episodes (id, name, air_date, episode) VALUES (7, 'Raising Gazorpazorp', 'March 10, 2014', 'S01E07');
INSERT INTO episodes (id, name, air_date, episode) VALUES (8, 'Rixty Minutes', 'March 17, 2014', 'S01E08');
INSERT INTO episodes (id, name, air_date, episode) VALUES (9, 'Something Ricked This Way Comes', 'March 24, 2014', 'S01E09');
INSERT INTO episodes (id, name, air_date, episode) VALUES (10, 'Close Rick-counters of the Rick Kind', 'April 7, 2014', 'S01E10');
INSERT INTO episodes (id, name, air_date, episode) VALUES (11, 'Ricksy Business', 'April 14, 2014', 'S01E11');
INSERT INTO episodes (id, name, air_date, episode) VALUES (12, 'A Rickle in Time', 'July 26, 2015', 'S02E01');
INSERT INTO episodes (id, name, air_date, episode) VALUES (13, 'Mortynight Run', 'August 2, 2015', 'S02E02');
INSERT INTO episodes (id, name, air_date, episode) VALUES (14, 'Auto Erotic Assimilation', 'August 9, 2015', 'S02E03');
INSERT INTO episodes (id, name, air_date, episode) VALUES (15, 'Total Rickall', 'August 16, 2015', 'S02E04');
INSERT INTO episodes (id, name, air_date, episode) VALUES (16, 'Get Schwifty', 'August 23, 2015', 'S02E05');
INSERT INTO episodes (id, name, air_date, episode) VALUES (17, 'The Ricks Must Be Crazy', 'August 30, 2015', 'S02E06');
INSERT INTO episodes (id, name, air_date, episode) VALUES (18, 'Big Trouble in Little Sanchez', 'September 13, 2015', 'S02E07');
INSERT INTO episodes (id, name, air_date, episode) VALUES (19, 'Interdimensional Cable 2: Tempting Fate', 'September 20, 2015', 'S02E08');
INSERT INTO episodes (id, name, air_date, episode) VALUES (20, 'Look Who''s Purging Now', 'September 27, 2015', 'S02E09');
INSERT INTO episodes (id, name, air_date, episode) VALUES (21, 'The Wedding Squanchers', 'October 4, 2015', 'S02E10');
INSERT INTO episodes (id, name, air_date, episode) VALUES (22, 'The Rickshank Rickdemption', 'April 1, 2017', 'S03E01');
INSERT INTO episodes (id, name, air_date, episode) VALUES (23, 'Rickmancing the Stone', 'July 30, 2017', 'S03E02');
INSERT INTO episodes (id, name, air_date, episode) VALUES (24, 'Pickle Rick', 'August 6, 2017', 'S03E03');
INSERT INTO episodes (id, name, air_date, episode) VALUES (25, 'Vindicators 3: The Return of Worldender', 'August 13, 2017', 'S03E04');
INSERT INTO episodes (id, name, air_date, episode) VALUES (26, 'The Whirly Dirly Conspiracy', 'August 20, 2017', 'S03E05');
INSERT INTO episodes (id, name, air_date, episode) VALUES (27, 'Rest and Ricklaxation', 'August 27, 2017', 'S03E06');
INSERT INTO episodes (id, name, air_date, episode) VALUES (28, 'The Ricklantis Mixup', 'September 10, 2017', 'S03E07');
INSERT INTO episodes (id, name, air_date, episode) VALUES (29, 'Morty''s Mind Blowers', 'September 17, 2017', 'S03E08');
INSERT INTO episodes (id, name, air_date, episode) VALUES (30, 'The ABC''s of Beth', 'September 24, 2017', 'S03E09');
INSERT INTO episodes (id, name, air_date, episode) VALUES (31, 'The Rickchurian Mortydate', 'October 1, 2017', 'S03E10');
INSERT INTO episodes (id, name, air_date, episode) VALUES (32, 'Edge of Tomorty: Rick, Die, Rickpeat', 'November 10, 2019', 'S04E01');
INSERT INTO episodes (id, name, air_date, episode) VALUES (33, 'The Old Man and the Seat', 'November 17, 2019', 'S04E02');
INSERT INTO episodes (id, name, air_date, episode) VALUES (34, 'One Crew Over the Crewcoo''s Morty', 'November 24, 2019', 'S04E03');
INSERT INTO episodes (id, name, air_date, episode) VALUES (35, 'Claw and Hoarder: Special Ricktim''s Morty', 'December 8, 2019', 'S04E04');
INSERT INTO episodes (id, name, air_date, episode) VALUES (36, 'Rattlestar Ricklactica', 'December 15, 2019', 'S04E05');
INSERT INTO episodes (id, name, air_date, episode) VALUES (37, 'Never Ricking Morty', 'May 3, 2020', 'S04E06');
INSERT INTO episodes (id, name, air_date, episode) VALUES (38, 'Promortyus', 'May 10, 2020', 'S04E07');
INSERT INTO episodes (id, name, air_date, episode) VALUES (39, 'The Vat of Acid Episode', 'May 17, 2020', 'S04E08');
INSERT INTO episodes (id, name, air_date, episode) VALUES (40, 'Childrick of Mort', 'May 24, 2020', 'S04E09');
INSERT INTO episodes (id, name, air_date, episode) VALUES (41, 'Star Mort: Rickturn of the Jerri', 'May 31, 2020', 'S04E10');
INSERT INTO episodes (id, name, air_date, episode) VALUES (42, 'Mort Dinner Rick Andre', 'June 20, 2021', 'S05E01');
INSERT INTO episodes (id, name, air_date, episode) VALUES (43, 'Mortyplicity', 'June 27, 2021', 'S05E02');
INSERT INTO episodes (id, name, air_date, episode) VALUES (44, 'A Rickconvenient Mort', 'July 4, 2021', 'S05E03');
INSERT INTO episodes (id, name, air_date, episode) VALUES (45, 'Rickdependence Spray', 'July 11, 2021', 'S05E04');
INSERT INTO episodes (id, name, air_date, episode) VALUES (46, 'Amortycan Grickfitti', 'July 18, 2021', 'S05E05');
INSERT INTO episodes (id, name, air_date, episode) VALUES (47, 'Rick & Morty''s Thanksploitation Spectacular', 'July 25, 2021', 'S05E06');
INSERT INTO episodes (id, name, air_date, episode) VALUES (48, 'Gotron Jerrysis Rickvangelion', 'August 1, 2021', 'S05E07');
INSERT INTO episodes (id, name, air_date, episode) VALUES (49, 'Rickternal Friendshine of the Spotless Mort', 'August 8, 2021', 'S05E08');
INSERT INTO episodes (id, name, air_date, episode) VALUES (50, 'Forgetting Sarick Mortshall', 'September 5, 2021', 'S05E09');
INSERT INTO episodes (id, name, air_date, episode) VALUES (51, 'Rickmurai Jack', 'September 5, 2021', 'S05E10');
INSERT INTO location_residents (location_id, character_id) VALUES
  (1, 38),
  (1, 45),
  (1, 71),
  (1, 82),
  (1, 83),
  (1, 92),
  (1, 112),
  (1, 114),
  (1, 116),
  (1, 117),
  (1, 120),
  (1, 127),
  (1, 155),
  (1, 169),
  (1, 175),
  (1, 179),
  (1, 186),
  (1, 201),
  (1, 216),
  (1, 239),
  (1, 271),
  (1, 302),
  (1, 303),
  (1, 338),
  (1, 343),
  (1, 356),
  (1, 394),
  (2, 6),
  (3, 8),
  (3, 14),
  (3, 15),
  (3, 18),
  (3, 21),
  (3, 22),
  (3, 27),
  (3, 42),
  (3, 43),
  (3, 44),
  (3, 48),
  (3, 53),
  (3, 56),
  (3, 61),
  (3, 69),
  (3, 72),
  (3, 73),
  (3, 74),
  (3, 77),
  (3, 78),
  (3, 85),
  (3, 86),
  (3, 95),
  (3, 118),
  (3, 119),
  (3, 123),
  (3, 135),
  (3, 143),
  (3, 152),
  (3, 164),
  (3, 165),
  (3, 187),
  (3, 200),
  (3, 206),
  (3, 209),
  (3, 220),
  (3, 229),
  (3, 231),
  (3, 235),
  (3, 267),
  (3, 278),
  (3, 281),
  (3, 283),
  (3, 284),
  (3, 285),
  (3, 286),
  (3, 287),
  (3, 288),
  (3, 289),
  (3, 291),
  (3, 295),
  (3, 298),
  (3, 299),
  (3, 322),
  (3, 325),
  (3, 328),
  (3, 330),
  (3, 345),
  (3, 359),
  (3, 366),
  (3, 378),
  (3, 385),
  (3, 392),
  (3, 461),
  (3, 462),
  (3, 463),
  (3, 464),
  (3, 465),
  (3, 466),
  (3, 472),
  (3, 473),
  (3, 474),
  (3, 475),
  (3, 476),
  (3, 477),
  (3, 478),
  (3, 479),
  (3, 480),
  (3, 481),
  (3, 482),
  (3, 483),
  (3, 484),
  (3, 485),
  (3, 486),
  (3, 487),
  (3, 488),
  (3, 489),
  (3, 2),
  (3, 1),
  (3, 801),
  (3, 802),
  (3, 803),
  (3, 804),
  (3, 805),
  (3, 806),
  (3, 810),
  (3, 811),
  (3, 812),
  (3, 819),
  (3, 820),
  (3, 818),
  (4, 10),
  (4, 81),
  (4, 208),
  (4, 226),
  (4, 340),
  (4, 362),
  (4, 375),
  (4, 382),
  (4, 395),
  (5, 12),
  (5, 17),
  (5, 96),
  (5, 97),
  (5, 98),
  (5, 99),
  (5, 100),
  (5, 101),
  (5, 108),
  (5, 268),
  (5, 300),
  (6, 20),
  (6, 28),
  (6, 29),
  (6, 34),
  (6, 49),
  (6, 51),
  (6, 54),
  (6, 121),
  (6, 126),
  (6, 129),
  (6, 134),
  (6, 136),
  (6, 145),
  (6, 157),
  (6, 173),
  (6, 184),
  (6, 205),
  (6, 207),
  (6, 214),
  (6, 222),
  (6, 223),
  (6, 224),
  (6, 225),
  (6, 250),
  (6, 254),
  (6, 260),
  (6, 264),
  (6, 266),
  (6, 275),
  (6, 277),
  (6, 279),
  (6, 312),
  (6, 314),
  (6, 315),
  (6, 316),
  (6, 317),
  (6, 318),
  (6, 334),
  (6, 351),
  (6, 358),
  (6, 367),
  (6, 370),
  (6, 373),
  (6, 403),
  (6, 406),
  (6, 407),
  (6, 408),
  (6, 409),
  (6, 410),
  (6, 411),
  (6, 412),
  (6, 413),
  (6, 414),
  (6, 415),
  (6, 416),
  (6, 417),
  (6, 418),
  (6, 457),
  (6, 458),
  (6, 459),
  (6, 709),
  (6, 711),
  (7, 23),
  (7, 204),
  (7, 320),
  (8, 25),
  (8, 52),
  (8, 68),
  (8, 110),
  (8, 111),
  (8, 140),
  (8, 156),
  (8, 228),
  (8, 323),
  (8, 342),
  (9, 26),
  (9, 139),
  (9, 202),
  (9, 273),
  (10, 33),
  (11, 35),
  (13, 40),
  (13, 55),
  (13, 131),
  (13, 132),
  (13, 146),
  (13, 148),
  (13, 163),
  (13, 178),
  (13, 310),
  (13, 386),
  (13, 387),
  (14, 89),
  (14, 399),
  (14, 400),
  (16, 105),
  (16, 263),
  (16, 321),
  (16, 383),
  (16, 384),
  (16, 454),
  (16, 455),
  (16, 456),
  (16, 460),
  (18, 63),
  (18, 80),
  (18, 221),
  (18, 246),
  (18, 304),
  (18, 305),
  (18, 306),
  (18, 396),
  (20, 3),
  (20, 4),
  (20, 5),
  (20, 9),
  (20, 11),
  (20, 13),
  (20, 16),
  (20, 31),
  (20, 32),
  (20, 50),
  (20, 58),
  (20, 59),
  (20, 64),
  (20, 66),
  (20, 76),
  (20, 88),
  (20, 103),
  (20, 107),
  (20, 109),
  (20, 113),
  (20, 115),
  (20, 124),
  (20, 128),
  (20, 137),
  (20, 138),
  (20, 141),
  (20, 147),
  (20, 149),
  (20, 151),
  (20, 154),
  (20, 166),
  (20, 167),
  (20, 170),
  (20, 171),
  (20, 172),
  (20, 180),
  (20, 181),
  (20, 182),
  (20, 185),
  (20, 189),
  (20, 190),
  (20, 210),
  (20, 217),
  (20, 218),
  (20, 219),
  (20, 227),
  (20, 230),
  (20, 233),
  (20, 234),
  (20, 236),
  (20, 237),
  (20, 240),
  (20, 241),
  (20, 243),
  (20, 244),
  (20, 245),
  (20, 248),
  (20, 251),
  (20, 255),
  (20, 259),
  (20, 262),
  (20, 265),
  (20, 272),
  (20, 276),
  (20, 280),
  (20, 292),
  (20, 293),
  (20, 324),
  (20, 326),
  (20, 327),
  (20, 332),
  (20, 335),
  (20, 341),
  (20, 346),
  (20, 347),
  (20, 352),
  (20, 353),
  (20, 354),
  (20, 357),
  (20, 360),
  (20, 361),
  (20, 363),
  (20, 365),
  (20, 374),
  (20, 377),
  (20, 390),
  (20, 391),
  (20, 401),
  (20, 402),
  (20, 405),
  (20, 423),
  (20, 435),
  (20, 437),
  (20, 438),
  (20, 439),
  (20, 440),
  (20, 452),
  (20, 453),
  (20, 467),
  (20, 468),
  (20, 469),
  (20, 471),
  (20, 492),
  (20, 493),
  (20, 497),
  (20, 509),
  (20, 510),
  (20, 511),
  (20, 512),
  (20, 513),
  (20, 514),
  (20, 516),
  (20, 517),
  (20, 523),
  (20, 524),
  (20, 526),
  (20, 534),
  (20, 535),
  (20, 536),
  (20, 537),
  (20, 538),
  (20, 539),
  (20, 540),
  (20, 541),
  (20, 561),
  (20, 562),
  (20, 564),
  (20, 570),
  (20, 575),
  (20, 584),
  (20, 585),
  (20, 586),
  (20, 588),
  (20, 590),
  (20, 591),
  (20, 592),
  (20, 667),
  (20, 672),
  (20, 680),
  (20, 684),
  (20, 686),
  (20, 687),
  (20, 688),
  (20, 689),
  (20, 690),
  (20, 691),
  (20, 692),
  (20, 693),
  (20, 694),
  (20, 695),
  (20, 696),
  (20, 697),
  (20, 698),
  (20, 699),
  (20, 700),
  (20, 701),
  (20, 702),
  (20, 703),
  (20, 704),
  (20, 705),
  (20, 706),
  (20, 707),
  (20, 708),
  (20, 710),
  (20, 712),
  (20, 713),
  (20, 714),
  (20, 715),
  (20, 716),
  (20, 717),
  (20, 719),
  (20, 720),
  (20, 721),
  (20, 722),
  (20, 723),
  (20, 724),
  (20, 725),
  (20, 726),
  (20, 727),
  (20, 728),
  (20, 729),
  (20, 730),
  (20, 732),
  (20, 733),
  (20, 734),
  (20, 735),
  (20, 737),
  (20, 738),
  (20, 739),
  (20, 740),
  (20, 741),
  (20, 742),
  (20, 753),
  (20, 754),
  (20, 755),
  (20, 756),
  (20, 757),
  (20, 758),
  (20, 759),
  (20, 760),
  (20, 761),
  (20, 762),
  (20, 763),
  (20, 764),
  (20, 765),
  (20, 766),
  (20, 767),
  (20, 768),
  (20, 769),
  (20, 770),
  (20, 771),
  (20, 772),
  (20, 773),
  (20, 774),
  (20, 775),
  (20, 776),
  (20, 777),
  (20, 778),
  (20, 781),
  (20, 786),
  (20, 788),
  (20, 789),
  (20, 790),
  (20, 791),
  (20, 203),
  (20, 794),
  (20, 800),
  (20, 813),
  (20, 821),
  (20, 826),
  (21, 7),
  (21, 436),
  (22, 24),
  (22, 309),
  (23, 37),
  (23, 91),
  (23, 176),
  (23, 183),
  (23, 195);
INSERT INTO location_residents (location_id, character_id) VALUES
  (24, 65),
  (24, 388),
  (25, 67),
  (25, 490),
  (25, 491),
  (27, 79),
  (27, 84),
  (28, 90),
  (28, 188),
  (28, 301),
  (28, 336),
  (28, 355),
  (28, 372),
  (29, 93),
  (29, 104),
  (29, 198),
  (30, 822),
  (30, 823),
  (30, 824),
  (30, 825),
  (32, 106),
  (32, 393),
  (32, 449),
  (32, 450),
  (32, 451),
  (34, 39),
  (34, 177),
  (34, 232),
  (34, 290),
  (34, 339),
  (35, 47),
  (35, 75),
  (35, 102),
  (35, 194),
  (35, 199),
  (35, 256),
  (35, 261),
  (35, 308),
  (35, 311),
  (35, 331),
  (35, 379),
  (37, 142),
  (37, 296),
  (37, 297),
  (37, 319),
  (37, 369),
  (38, 144),
  (38, 158),
  (39, 150),
  (40, 168),
  (40, 211),
  (40, 376),
  (41, 153),
  (42, 159),
  (42, 160),
  (43, 161),
  (43, 162),
  (43, 212),
  (43, 213),
  (43, 253),
  (44, 174),
  (44, 257),
  (44, 441),
  (44, 442),
  (44, 443),
  (44, 444),
  (44, 445),
  (44, 446),
  (46, 87),
  (46, 191),
  (46, 270),
  (46, 337),
  (47, 192),
  (47, 307),
  (47, 419),
  (47, 420),
  (47, 421),
  (47, 422),
  (48, 41),
  (48, 193),
  (48, 238),
  (48, 333),
  (49, 348),
  (50, 197),
  (50, 364),
  (54, 252),
  (55, 258),
  (56, 269),
  (57, 282),
  (57, 447),
  (57, 448),
  (63, 350),
  (66, 371),
  (70, 397),
  (70, 398),
  (71, 424),
  (71, 425),
  (72, 404),
  (72, 427),
  (72, 428),
  (73, 426),
  (74, 429),
  (74, 430),
  (74, 431),
  (74, 432),
  (76, 470),
  (77, 242),
  (77, 498),
  (77, 499),
  (77, 500),
  (77, 501),
  (77, 502),
  (78, 313),
  (78, 571),
  (78, 572),
  (78, 573),
  (78, 574),
  (78, 576),
  (78, 577),
  (78, 578),
  (78, 579),
  (78, 580),
  (78, 581),
  (78, 582),
  (78, 583),
  (78, 587),
  (78, 589),
  (79, 494),
  (79, 495),
  (79, 496),
  (80, 503),
  (80, 504),
  (80, 505),
  (80, 506),
  (81, 507),
  (81, 508),
  (82, 515),
  (82, 518),
  (82, 519),
  (82, 520),
  (82, 521),
  (82, 522),
  (83, 525),
  (83, 527),
  (83, 528),
  (83, 533),
  (84, 543),
  (85, 529),
  (85, 530),
  (86, 531),
  (87, 532),
  (87, 542),
  (88, 544),
  (88, 545),
  (88, 546),
  (88, 547),
  (88, 548),
  (88, 549),
  (88, 550),
  (88, 551),
  (88, 552),
  (88, 553),
  (89, 554),
  (89, 555),
  (89, 556),
  (89, 557),
  (89, 558),
  (93, 559),
  (93, 560),
  (94, 563),
  (94, 565),
  (94, 566),
  (94, 567),
  (94, 568),
  (94, 569),
  (96, 597),
  (96, 598),
  (96, 599),
  (96, 600),
  (96, 601),
  (96, 606),
  (96, 607),
  (96, 611),
  (96, 612),
  (96, 615),
  (96, 616),
  (96, 617),
  (96, 618),
  (96, 619),
  (96, 620),
  (96, 622),
  (96, 623),
  (96, 624),
  (96, 626),
  (96, 627),
  (96, 630),
  (96, 631),
  (96, 632),
  (96, 633),
  (96, 634),
  (96, 637),
  (96, 638),
  (97, 593),
  (98, 594),
  (98, 595),
  (98, 596),
  (98, 605),
  (98, 609),
  (98, 610),
  (98, 639),
  (99, 602),
  (99, 604),
  (99, 613),
  (99, 628),
  (99, 629),
  (99, 621),
  (100, 603),
  (100, 614),
  (100, 625),
  (100, 635),
  (100, 636),
  (100, 608),
  (101, 640),
  (101, 641),
  (101, 642),
  (101, 643),
  (101, 644),
  (101, 645),
  (101, 646),
  (101, 647),
  (102, 648),
  (102, 649),
  (102, 650),
  (102, 651),
  (103, 652),
  (103, 656),
  (103, 657),
  (103, 658),
  (103, 661),
  (103, 655),
  (104, 653),
  (104, 654),
  (104, 659),
  (104, 660),
  (105, 344),
  (105, 670),
  (105, 671),
  (106, 662),
  (106, 663),
  (106, 664),
  (106, 665),
  (106, 666),
  (107, 668),
  (108, 669),
  (110, 673),
  (110, 674),
  (110, 675),
  (110, 676),
  (110, 677),
  (110, 678),
  (110, 679),
  (110, 681),
  (110, 682),
  (110, 683),
  (110, 685),
  (112, 799),
  (113, 718),
  (113, 798),
  (115, 731),
  (115, 736),
  (116, 744),
  (116, 745),
  (116, 746),
  (116, 747),
  (116, 748),
  (116, 749),
  (116, 750),
  (117, 743),
  (118, 751),
  (118, 752),
  (120, 780),
  (120, 782),
  (120, 783),
  (120, 784),
  (120, 785),
  (122, 792),
  (122, 793),
  (123, 795),
  (123, 796),
  (124, 797),
  (125, 809),
  (125, 808),
  (125, 787),
  (126, 815),
  (126, 814),
  (126, 807),
  (126, 94),
  (126, 779),
  (126, 816),
  (126, 817),
  (126, 274),
  (126, 389),
  (126, 215),
  (126, 294);
INSERT INTO character_episodes (character_id, episode_id) VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (1, 4),
  (1, 5),
  (1, 6),
  (1, 7),
  (1, 8),
  (1, 9),
  (1, 10),
  (1, 11),
  (1, 12),
  (1, 13),
  (1, 14),
  (1, 15),
  (1, 16),
  (1, 17),
  (1, 18),
  (1, 19),
  (1, 20),
  (1, 21),
  (1, 22),
  (1, 23),
  (1, 24),
  (1, 25),
  (1, 26),
  (1, 27),
  (1, 28),
  (1, 29),
  (1, 30),
  (1, 31),
  (1, 32),
  (1, 33),
  (1, 34),
  (1, 35),
  (1, 36),
  (1, 37),
  (1, 38),
  (1, 39),
  (1, 40),
  (1, 41),
  (1, 42),
  (1, 43),
  (1, 44),
  (1, 45),
  (1, 46),
  (1, 47),
  (1, 48),
  (1, 49),
  (1, 50),
  (1, 51),
  (2, 1),
  (2, 2),
  (2, 3),
  (2, 4),
  (2, 5),
  (2, 6),
  (2, 7),
  (2, 8),
  (2, 9),
  (2, 10),
  (2, 11),
  (2, 12),
  (2, 13),
  (2, 14),
  (2, 15),
  (2, 16),
  (2, 17),
  (2, 18),
  (2, 19),
  (2, 20),
  (2, 21),
  (2, 22),
  (2, 23),
  (2, 24),
  (2, 25),
  (2, 26),
  (2, 27),
  (2, 28),
  (2, 29),
  (2, 30),
  (2, 31),
  (2, 32),
  (2, 33),
  (2, 34),
  (2, 35),
  (2, 36),
  (2, 37),
  (2, 38),
  (2, 39),
  (2, 40),
  (2, 41),
  (2, 42),
  (2, 43),
  (2, 44),
  (2, 45),
  (2, 46),
  (2, 47),
  (2, 48),
  (2, 49),
  (2, 50),
  (2, 51),
  (3, 6),
  (3, 7),
  (3, 8),
  (3, 9),
  (3, 10),
  (3, 11),
  (3, 12),
  (3, 14),
  (3, 15),
  (3, 16),
  (3, 17),
  (3, 18),
  (3, 19),
  (3, 20),
  (3, 21),
  (3, 22),
  (3, 23),
  (3, 24),
  (3, 25),
  (3, 26),
  (3, 27),
  (3, 29),
  (3, 30),
  (3, 31),
  (3, 32),
  (3, 33),
  (3, 34),
  (3, 35),
  (3, 36),
  (3, 38),
  (3, 39),
  (3, 40),
  (3, 41),
  (3, 42),
  (3, 43),
  (3, 44),
  (3, 45),
  (3, 46),
  (3, 47),
  (3, 48),
  (3, 49),
  (3, 51),
  (4, 6),
  (4, 7),
  (4, 8),
  (4, 9),
  (4, 10),
  (4, 11),
  (4, 12),
  (4, 14),
  (4, 15),
  (4, 16),
  (4, 18),
  (4, 19),
  (4, 20),
  (4, 21),
  (4, 22),
  (4, 23),
  (4, 24),
  (4, 25),
  (4, 26),
  (4, 27),
  (4, 28),
  (4, 29),
  (4, 30),
  (4, 31),
  (4, 32),
  (4, 33),
  (4, 34),
  (4, 35),
  (4, 36),
  (4, 38),
  (4, 39),
  (4, 40),
  (4, 41),
  (4, 42),
  (4, 43),
  (4, 44),
  (4, 45),
  (4, 46),
  (4, 47),
  (4, 48),
  (4, 49),
  (4, 51),
  (5, 6),
  (5, 7),
  (5, 8),
  (5, 9),
  (5, 10),
  (5, 11),
  (5, 12),
  (5, 13),
  (5, 14),
  (5, 15),
  (5, 16),
  (5, 18),
  (5, 19),
  (5, 20),
  (5, 21),
  (5, 22),
  (5, 23),
  (5, 26),
  (5, 29),
  (5, 30),
  (5, 31),
  (5, 32),
  (5, 33),
  (5, 35),
  (5, 36),
  (5, 38),
  (5, 39),
  (5, 40),
  (5, 41),
  (5, 42),
  (5, 43),
  (5, 44),
  (5, 45),
  (5, 46),
  (5, 47),
  (5, 48),
  (5, 49),
  (5, 50),
  (5, 51),
  (6, 27),
  (7, 10),
  (7, 11),
  (8, 28),
  (9, 24),
  (10, 25),
  (11, 12),
  (12, 3),
  (13, 31),
  (14, 10),
  (15, 10),
  (16, 15),
  (17, 3),
  (18, 10),
  (18, 28),
  (19, 10),
  (20, 8),
  (21, 10),
  (21, 22),
  (22, 10),
  (22, 22),
  (22, 28),
  (23, 13),
  (23, 19),
  (23, 21),
  (23, 25),
  (23, 26),
  (24, 16),
  (25, 23),
  (26, 20),
  (27, 10),
  (27, 28),
  (28, 8),
  (28, 13),
  (28, 17),
  (29, 8),
  (30, 31),
  (31, 15),
  (32, 15),
  (33, 29),
  (34, 8),
  (34, 13),
  (34, 17),
  (35, 1),
  (35, 11),
  (35, 19),
  (35, 25),
  (36, 14),
  (37, 8),
  (38, 1),
  (38, 2),
  (38, 3),
  (38, 4),
  (38, 5),
  (38, 6),
  (38, 22),
  (38, 51),
  (39, 10),
  (40, 18),
  (41, 5),
  (42, 22),
  (43, 28),
  (44, 28),
  (45, 3),
  (46, 2),
  (47, 11),
  (47, 16),
  (47, 21),
  (47, 22),
  (47, 26),
  (47, 49),
  (47, 51),
  (48, 22),
  (48, 28),
  (49, 19),
  (50, 14),
  (51, 19),
  (52, 23),
  (53, 10),
  (54, 8),
  (55, 18),
  (55, 21),
  (56, 28),
  (57, 22),
  (58, 6),
  (58, 11),
  (58, 30),
  (58, 46),
  (59, 7),
  (60, 25),
  (61, 28),
  (62, 1),
  (63, 2),
  (64, 12),
  (65, 17),
  (66, 18),
  (67, 29),
  (68, 23),
  (69, 22),
  (70, 24),
  (71, 22),
  (72, 28),
  (73, 28),
  (74, 28),
  (75, 21),
  (76, 15),
  (77, 10),
  (78, 10),
  (78, 28),
  (79, 10),
  (80, 2),
  (81, 25),
  (82, 6),
  (82, 10),
  (83, 6),
  (83, 10),
  (84, 10),
  (85, 10),
  (85, 28),
  (86, 10),
  (86, 22),
  (86, 28),
  (87, 4),
  (88, 8),
  (88, 9),
  (88, 11),
  (88, 25),
  (89, 5),
  (90, 14),
  (91, 8),
  (92, 1),
  (92, 6),
  (93, 25),
  (94, 22),
  (94, 51),
  (95, 22),
  (96, 3),
  (97, 3),
  (98, 3),
  (99, 3),
  (100, 3),
  (101, 3),
  (102, 21),
  (103, 10),
  (103, 22),
  (104, 25),
  (105, 19),
  (106, 13),
  (107, 24),
  (107, 41),
  (108, 3),
  (109, 15),
  (110, 23),
  (111, 23),
  (112, 3),
  (113, 10),
  (114, 3),
  (115, 16),
  (115, 26),
  (116, 5),
  (117, 5),
  (118, 10),
  (118, 28),
  (118, 51),
  (119, 10),
  (120, 5),
  (121, 19),
  (122, 13),
  (123, 28),
  (124, 16),
  (124, 27),
  (125, 18),
  (125, 21),
  (125, 25),
  (126, 19),
  (127, 1),
  (128, 15),
  (129, 8),
  (129, 13),
  (129, 17),
  (130, 21),
  (131, 13),
  (131, 18),
  (131, 21),
  (132, 18),
  (133, 13),
  (133, 19),
  (133, 21),
  (134, 8),
  (135, 28),
  (136, 8),
  (136, 13),
  (137, 26),
  (137, 49),
  (138, 16),
  (139, 20),
  (140, 23),
  (141, 15),
  (142, 26),
  (143, 28),
  (144, 1),
  (145, 8),
  (146, 18),
  (147, 29),
  (148, 18),
  (149, 29),
  (150, 22),
  (151, 7),
  (152, 10),
  (152, 22),
  (153, 8),
  (153, 19),
  (154, 15),
  (155, 6),
  (156, 23),
  (157, 8),
  (158, 1),
  (159, 17),
  (160, 17),
  (161, 16),
  (162, 16),
  (163, 18),
  (164, 10),
  (165, 28),
  (166, 31),
  (167, 24),
  (168, 7),
  (169, 3),
  (169, 15),
  (170, 27),
  (171, 24),
  (172, 16),
  (173, 19),
  (174, 13),
  (175, 1),
  (175, 2),
  (175, 3),
  (175, 4),
  (175, 5),
  (175, 6),
  (175, 22),
  (176, 8),
  (177, 10),
  (178, 18),
  (179, 1),
  (179, 4),
  (179, 6),
  (180, 11),
  (180, 13),
  (180, 17),
  (180, 18),
  (180, 26),
  (180, 27),
  (180, 28),
  (180, 29),
  (180, 30),
  (180, 32),
  (180, 39),
  (180, 42),
  (181, 1),
  (181, 4),
  (181, 6),
  (181, 11),
  (181, 17),
  (181, 18),
  (181, 25),
  (181, 27),
  (182, 16),
  (183, 8),
  (184, 8),
  (185, 30),
  (186, 3),
  (187, 28),
  (188, 14),
  (189, 24);
INSERT INTO character_episodes (character_id, episode_id) VALUES
  (190, 30),
  (191, 4),
  (192, 9),
  (193, 5),
  (194, 21),
  (195, 8),
  (196, 13),
  (197, 17),
  (198, 25),
  (199, 16),
  (199, 19),
  (199, 21),
  (200, 22),
  (201, 3),
  (202, 20),
  (203, 21),
  (203, 48),
  (204, 26),
  (205, 19),
  (206, 28),
  (207, 8),
  (207, 13),
  (207, 17),
  (208, 25),
  (209, 10),
  (210, 11),
  (211, 7),
  (212, 16),
  (213, 16),
  (214, 8),
  (215, 10),
  (215, 22),
  (215, 51),
  (216, 6),
  (216, 11),
  (216, 25),
  (217, 23),
  (218, 23),
  (219, 23),
  (220, 28),
  (221, 2),
  (222, 8),
  (223, 19),
  (224, 19),
  (225, 19),
  (226, 25),
  (227, 27),
  (228, 23),
  (229, 28),
  (230, 7),
  (231, 22),
  (232, 10),
  (233, 28),
  (234, 6),
  (235, 28),
  (236, 15),
  (237, 12),
  (238, 5),
  (239, 1),
  (239, 2),
  (239, 4),
  (239, 6),
  (240, 9),
  (240, 16),
  (240, 17),
  (240, 18),
  (240, 21),
  (240, 22),
  (240, 24),
  (240, 27),
  (240, 30),
  (240, 39),
  (241, 4),
  (241, 16),
  (242, 5),
  (242, 10),
  (242, 13),
  (242, 29),
  (242, 32),
  (243, 9),
  (244, 15),
  (244, 21),
  (244, 29),
  (244, 30),
  (244, 31),
  (244, 34),
  (244, 51),
  (245, 30),
  (246, 2),
  (246, 27),
  (247, 31),
  (248, 15),
  (249, 1),
  (249, 6),
  (249, 14),
  (249, 30),
  (250, 8),
  (251, 6),
  (251, 9),
  (251, 11),
  (251, 25),
  (251, 29),
  (251, 36),
  (252, 25),
  (253, 16),
  (254, 19),
  (255, 16),
  (256, 21),
  (257, 13),
  (258, 7),
  (259, 15),
  (260, 19),
  (261, 21),
  (262, 15),
  (263, 19),
  (264, 19),
  (265, 24),
  (266, 8),
  (266, 17),
  (267, 28),
  (268, 3),
  (269, 31),
  (270, 4),
  (271, 1),
  (271, 5),
  (271, 6),
  (272, 9),
  (272, 16),
  (272, 18),
  (272, 24),
  (272, 27),
  (272, 29),
  (273, 20),
  (274, 10),
  (274, 22),
  (274, 51),
  (275, 19),
  (276, 24),
  (277, 8),
  (278, 28),
  (279, 8),
  (280, 15),
  (281, 28),
  (282, 11),
  (282, 13),
  (282, 25),
  (283, 28),
  (284, 28),
  (285, 22),
  (285, 51),
  (286, 22),
  (287, 28),
  (288, 28),
  (289, 28),
  (290, 10),
  (291, 28),
  (292, 28),
  (293, 6),
  (294, 10),
  (294, 22),
  (294, 51),
  (295, 10),
  (295, 11),
  (295, 22),
  (296, 26),
  (297, 26),
  (298, 10),
  (299, 10),
  (300, 3),
  (301, 14),
  (302, 3),
  (303, 5),
  (304, 2),
  (305, 2),
  (306, 2),
  (307, 9),
  (308, 11),
  (308, 21),
  (309, 13),
  (309, 16),
  (309, 21),
  (309, 25),
  (310, 18),
  (311, 13),
  (311, 21),
  (311, 25),
  (312, 19),
  (313, 12),
  (313, 36),
  (314, 8),
  (315, 8),
  (316, 8),
  (317, 8),
  (318, 8),
  (319, 26),
  (320, 26),
  (321, 19),
  (322, 28),
  (323, 23),
  (324, 15),
  (325, 28),
  (326, 5),
  (326, 11),
  (327, 11),
  (328, 28),
  (329, 2),
  (329, 7),
  (329, 10),
  (329, 15),
  (329, 16),
  (329, 24),
  (329, 29),
  (329, 30),
  (330, 10),
  (330, 22),
  (331, 11),
  (331, 16),
  (331, 21),
  (332, 27),
  (333, 5),
  (333, 11),
  (333, 25),
  (334, 19),
  (335, 31),
  (336, 14),
  (337, 4),
  (338, 1),
  (338, 2),
  (338, 3),
  (338, 4),
  (338, 5),
  (338, 6),
  (338, 22),
  (339, 10),
  (340, 25),
  (341, 20),
  (342, 23),
  (343, 5),
  (343, 6),
  (344, 11),
  (344, 16),
  (344, 21),
  (344, 22),
  (344, 41),
  (345, 28),
  (346, 16),
  (347, 16),
  (347, 31),
  (347, 43),
  (347, 45),
  (347, 47),
  (348, 17),
  (349, 10),
  (350, 30),
  (351, 8),
  (352, 15),
  (353, 18),
  (354, 18),
  (355, 14),
  (356, 3),
  (357, 30),
  (358, 8),
  (358, 18),
  (358, 21),
  (359, 10),
  (360, 27),
  (361, 27),
  (362, 11),
  (362, 13),
  (362, 19),
  (362, 21),
  (362, 25),
  (363, 30),
  (364, 17),
  (365, 26),
  (365, 27),
  (365, 36),
  (365, 38),
  (365, 46),
  (366, 28),
  (367, 8),
  (367, 28),
  (368, 29),
  (369, 26),
  (370, 8),
  (371, 19),
  (372, 14),
  (373, 8),
  (374, 18),
  (375, 25),
  (376, 7),
  (377, 29),
  (378, 22),
  (379, 21),
  (380, 22),
  (380, 51),
  (381, 10),
  (382, 25),
  (383, 19),
  (384, 19),
  (385, 22),
  (386, 13),
  (386, 18),
  (387, 18),
  (388, 17),
  (389, 10),
  (389, 11),
  (389, 22),
  (389, 51),
  (390, 29),
  (391, 15),
  (392, 28),
  (392, 51),
  (393, 13),
  (394, 1),
  (394, 6),
  (395, 1),
  (395, 11),
  (395, 16),
  (395, 25),
  (396, 2),
  (397, 2),
  (398, 2),
  (399, 5),
  (400, 5),
  (401, 7),
  (402, 8),
  (403, 8),
  (404, 8),
  (405, 2),
  (405, 8),
  (405, 10),
  (405, 11),
  (406, 8),
  (407, 8),
  (408, 8),
  (409, 8),
  (410, 8),
  (411, 8),
  (412, 8),
  (413, 8),
  (414, 8),
  (415, 8),
  (416, 8),
  (417, 8),
  (418, 8),
  (419, 9),
  (420, 9),
  (421, 9),
  (422, 9),
  (423, 11),
  (424, 10),
  (425, 10),
  (426, 10),
  (427, 10),
  (428, 10),
  (429, 10),
  (430, 10),
  (431, 10),
  (432, 10),
  (433, 10),
  (434, 10),
  (435, 1),
  (435, 11),
  (435, 19),
  (435, 25),
  (436, 11),
  (436, 13),
  (437, 12),
  (438, 12),
  (439, 12),
  (440, 12),
  (441, 13),
  (442, 13),
  (443, 13),
  (444, 13),
  (445, 13),
  (446, 13),
  (447, 13),
  (448, 13),
  (449, 13),
  (450, 13),
  (451, 13),
  (452, 16),
  (453, 18),
  (454, 16),
  (454, 19),
  (454, 21),
  (455, 19),
  (456, 19),
  (457, 19),
  (458, 19),
  (459, 19),
  (460, 19),
  (461, 22),
  (462, 22),
  (463, 22),
  (464, 22),
  (465, 22),
  (466, 22),
  (467, 26),
  (468, 26),
  (469, 26),
  (470, 27),
  (471, 27),
  (472, 28),
  (473, 28),
  (474, 28),
  (475, 28),
  (476, 28),
  (477, 28),
  (478, 28),
  (479, 28),
  (480, 28),
  (481, 28),
  (482, 28),
  (483, 28),
  (484, 28),
  (485, 28),
  (486, 28),
  (487, 28),
  (488, 28),
  (489, 28),
  (490, 29),
  (491, 29),
  (492, 30),
  (493, 31),
  (494, 32),
  (495, 32),
  (496, 32),
  (497, 32),
  (498, 32),
  (499, 32),
  (500, 32),
  (501, 32),
  (502, 32),
  (503, 32),
  (504, 32),
  (505, 32),
  (506, 32),
  (507, 32),
  (508, 32),
  (509, 32),
  (510, 32),
  (511, 32),
  (512, 32),
  (513, 32),
  (514, 32),
  (515, 32),
  (516, 32),
  (517, 32),
  (518, 32),
  (519, 32),
  (520, 32),
  (521, 32),
  (522, 32),
  (523, 32),
  (524, 32),
  (525, 33),
  (526, 33),
  (527, 33),
  (528, 33),
  (529, 33),
  (530, 33),
  (531, 33),
  (532, 33),
  (533, 33),
  (534, 33),
  (535, 33),
  (536, 33),
  (537, 33),
  (538, 33),
  (539, 33),
  (540, 33),
  (541, 33),
  (542, 33),
  (543, 33),
  (544, 34),
  (545, 34),
  (546, 34),
  (547, 34),
  (548, 34),
  (549, 34),
  (550, 34),
  (551, 34),
  (552, 34),
  (553, 34),
  (554, 34),
  (555, 34),
  (556, 34),
  (557, 34),
  (558, 34),
  (559, 34),
  (560, 34),
  (561, 34),
  (562, 35),
  (563, 35),
  (564, 35),
  (565, 35),
  (566, 35);
INSERT INTO character_episodes (character_id, episode_id) VALUES
  (567, 35),
  (568, 35),
  (569, 35),
  (570, 35),
  (571, 36),
  (572, 36),
  (573, 36),
  (574, 36),
  (575, 36),
  (576, 36),
  (577, 36),
  (578, 36),
  (579, 36),
  (580, 36),
  (581, 36),
  (582, 36),
  (583, 36),
  (584, 36),
  (585, 36),
  (586, 36),
  (587, 36),
  (588, 36),
  (589, 36),
  (590, 36),
  (591, 36),
  (592, 22),
  (592, 41),
  (592, 49),
  (593, 37),
  (594, 37),
  (595, 37),
  (596, 37),
  (597, 37),
  (598, 37),
  (599, 37),
  (600, 37),
  (601, 37),
  (602, 37),
  (603, 37),
  (604, 37),
  (605, 37),
  (606, 37),
  (607, 37),
  (608, 37),
  (609, 37),
  (610, 37),
  (611, 37),
  (612, 37),
  (613, 37),
  (614, 37),
  (615, 37),
  (616, 37),
  (617, 37),
  (618, 37),
  (619, 37),
  (620, 37),
  (621, 37),
  (622, 37),
  (623, 37),
  (624, 37),
  (625, 37),
  (626, 37),
  (627, 37),
  (628, 37),
  (629, 37),
  (630, 37),
  (631, 37),
  (632, 37),
  (633, 37),
  (634, 37),
  (635, 37),
  (636, 37),
  (637, 37),
  (638, 37),
  (639, 37),
  (640, 38),
  (641, 38),
  (642, 38),
  (643, 38),
  (644, 38),
  (645, 38),
  (646, 38),
  (647, 38),
  (648, 39),
  (649, 39),
  (650, 39),
  (651, 39),
  (652, 39),
  (653, 39),
  (654, 39),
  (655, 39),
  (656, 39),
  (657, 39),
  (658, 39),
  (659, 39),
  (660, 39),
  (661, 39),
  (662, 40),
  (663, 40),
  (664, 40),
  (665, 40),
  (666, 40),
  (667, 41),
  (667, 43),
  (668, 41),
  (669, 41),
  (670, 41),
  (671, 41),
  (672, 42),
  (673, 42),
  (674, 42),
  (675, 42),
  (676, 42),
  (677, 42),
  (678, 42),
  (679, 42),
  (680, 42),
  (681, 42),
  (682, 42),
  (683, 42),
  (684, 42),
  (685, 42),
  (686, 42),
  (687, 43),
  (688, 43),
  (689, 43),
  (690, 43),
  (691, 43),
  (692, 43),
  (693, 43),
  (694, 43),
  (695, 43),
  (696, 43),
  (697, 43),
  (698, 43),
  (699, 43),
  (700, 43),
  (701, 43),
  (702, 43),
  (703, 43),
  (704, 43),
  (705, 43),
  (706, 43),
  (707, 43),
  (708, 43),
  (709, 43),
  (710, 43),
  (711, 43),
  (712, 43),
  (713, 43),
  (714, 43),
  (715, 43),
  (716, 43),
  (717, 44),
  (718, 44),
  (719, 44),
  (720, 44),
  (721, 44),
  (722, 44),
  (723, 44),
  (724, 44),
  (725, 44),
  (726, 45),
  (727, 45),
  (728, 45),
  (729, 45),
  (730, 45),
  (731, 45),
  (731, 48),
  (732, 45),
  (733, 45),
  (734, 45),
  (735, 45),
  (736, 45),
  (737, 45),
  (738, 45),
  (739, 45),
  (740, 45),
  (741, 45),
  (742, 46),
  (743, 46),
  (744, 46),
  (745, 46),
  (746, 46),
  (747, 46),
  (748, 46),
  (749, 46),
  (750, 46),
  (751, 46),
  (752, 46),
  (753, 17),
  (753, 47),
  (754, 47),
  (755, 47),
  (756, 47),
  (757, 45),
  (757, 47),
  (758, 47),
  (759, 47),
  (760, 47),
  (761, 47),
  (762, 47),
  (763, 47),
  (764, 47),
  (765, 47),
  (766, 47),
  (767, 47),
  (768, 47),
  (769, 48),
  (770, 48),
  (771, 48),
  (772, 48),
  (773, 48),
  (774, 48),
  (775, 48),
  (776, 48),
  (777, 48),
  (778, 48),
  (779, 49),
  (780, 49),
  (781, 49),
  (782, 49),
  (783, 49),
  (784, 49),
  (785, 49),
  (786, 49),
  (787, 50),
  (787, 51),
  (788, 50),
  (789, 50),
  (790, 50),
  (791, 50),
  (792, 50),
  (793, 50),
  (794, 48),
  (795, 48),
  (796, 48),
  (797, 44),
  (798, 44),
  (799, 44),
  (800, 47),
  (801, 51),
  (802, 51),
  (803, 51),
  (804, 51),
  (805, 51),
  (806, 51),
  (807, 51),
  (808, 51),
  (809, 51),
  (810, 51),
  (811, 51),
  (812, 51),
  (813, 51),
  (814, 51),
  (815, 51),
  (816, 51),
  (817, 51),
  (818, 51),
  (819, 51),
  (820, 51),
  (821, 48),
  (822, 51),
  (823, 51),
  (824, 51),
  (825, 51),
  (826, 9);

COMMIT;

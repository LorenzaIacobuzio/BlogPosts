INSERT OR IGNORE INTO user (id, _username, _password) VALUES (1, 'thomas', '$2a$10$PM8d3OGi0MY05Jk/mi3xxeC48PjFfYgIIZ/RKDKC35ohs9xDmXDMW');
INSERT OR IGNORE INTO user (id, _username, _password) VALUES (2, 'santiago', '$2a$10$PM8d3OGi0MY05Jk/mi3xxeC48PjFfYgIIZ/RKDKC35ohs9xDmXDMW');
INSERT OR IGNORE INTO user (id, _username, _password) VALUES (3, 'ashanti', '$2a$10$PM8d3OGi0MY05Jk/mi3xxeC48PjFfYgIIZ/RKDKC35ohs9xDmXDMW');
INSERT OR IGNORE INTO user (id, _username, _password) VALUES (4, 'julia', '$2a$10$PM8d3OGi0MY05Jk/mi3xxeC48PjFfYgIIZ/RKDKC35ohs9xDmXDMW');
INSERT OR IGNORE INTO user (id, _username, _password) VALUES (5, 'cheng', '$2a$10$PM8d3OGi0MY05Jk/mi3xxeC48PjFfYgIIZ/RKDKC35ohs9xDmXDMW');

INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (1, 'Excepteur occaecat minim reprehenderit cupidatat dolore voluptate velit labore pariatur culpa esse mollit. Veniam ipsum amet eu dolor reprehenderit quis tempor pariatur labore. Tempor excepteur velit dolor commodo aute. Proident aute cillum dolor sint laborum tempor cillum voluptate minim. Amet qui eiusmod duis est labore cupidatat excepteur occaecat nulla.', 12, 5, 0.19, 'food,recipes,baking');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (2, 'Ea cillum incididunt consequat ullamco nisi aute labore cupidatat exercitation et sunt nostrud. Occaecat elit tempor ex anim non nulla sit culpa ipsum aliquip. In amet in Lorem ut enim. Consectetur ea officia reprehenderit pariatur magna eiusmod voluptate. Nostrud labore id adipisicing culpa sunt veniam qui deserunt magna sint mollit. Cillum irure pariatur occaecat amet reprehenderit nisi qui proident aliqua.', 104, 200, 0.7, 'travel,hotels');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (3, 'Voluptate consequat minim commodo nisi minim ut. Exercitation incididunt eiusmod qui duis enim sunt dolor sit nisi laboris qui enim mollit. Proident pariatur elit est elit consectetur. Velit anim eu culpa adipisicing esse consequat magna. Id do aliquip pariatur laboris consequat cupidatat voluptate incididunt sint ea.', 10, 32, 0.7, 'travel,airbnb,vacation');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (4, 'This is post 4', 50, 300, 0.4, 'vacation,spa');

INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (5, 'Nulla minim irure duis cillum dolore minim enim officia nulla ut. Tempor magna pariatur velit ea cillum reprehenderit. Commodo laborum ullamco est dolore ea nostrud excepteur cupidatat esse. Esse cupidatat velit aliquip aliquip consectetur duis veniam excepteur anim deserunt. Do irure id aute culpa deserunt aute sit ad irure ullamco enim non cupidatat.', 13, 14, 0.64, 'tech,music,spa');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (6, 'Id nulla sunt ipsum consectetur commodo deserunt exercitation nostrud consectetur. Aliquip irure Lorem non aliqua. Anim do eu consectetur adipisicing sunt mollit non.', 16, 57, 0.68, 'spa,art,fashion');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (7, 'Ullamco deserunt et eu aliqua est et consequat fugiat sunt adipisicing ipsum. Incididunt fugiat esse amet dolore sunt quis officia minim minim. Esse ullamco duis eu qui enim in nulla enim eu aliquip nisi sunt laboris. Est commodo aliquip dolor nulla anim.', 11, 38, 0.2, 'vacation,fashion,food');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (8, 'Ex labore cillum aute in proident nostrud in. Adipisicing tempor Lorem occaecat ea quis ad ex velit sit cillum adipisicing. Adipisicing dolore velit aliqua in sunt duis ad adipisicing. Ut duis sit deserunt mollit velit cillum aute commodo ea nisi. Laboris enim ex cillum tempor amet do proident eu consectetur. Adipisicing elit ipsum et sit sunt esse laborum enim laborum.', 0, 17, 0.06, 'art,hotel,beach');
INSERT OR IGNORE INTO post (id, text, likes, reads, popularity, tags) VALUES (9, 'Quis sint amet ex ea cillum. Cillum eiusmod sit dolor proident. Exercitation enim sunt tempor tempor laborum dolor enim esse irure. Labore ut sit culpa sunt nostrud laboris. Adipisicing proident ea amet duis cillum do quis ipsum nostrud elit occaecat qui veniam. Laborum eu nostrud laboris labore ipsum id non Lorem dolor.', 0, 71, 0.78, 'art,spa,beach');


INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (1, 1);

INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (2, 2);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (2, 3);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (2, 1);

INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (3, 3);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (3, 4);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (3, 5);

INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (4, 4);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (4, 5);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (4, 6);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (4, 7);

INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (5, 5);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (5, 6);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (5, 7);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (5, 8);
INSERT OR IGNORE INTO user_post (user_id, post_id) VALUES (5, 9);
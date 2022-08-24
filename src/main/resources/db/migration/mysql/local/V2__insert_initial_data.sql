
# INSERTS
INSERT INTO `users` VALUES (78,'Bosnia and Herzegovina','Amar','Kvakic'),
                           (82,'Croatia','Sandi','Slonjsak'),
                           (101,'USA','Tom','Brady');
INSERT INTO `followers` VALUES (101,78),(78,101);

INSERT INTO `tweets` VALUES (79,'2022-07-29 15:25:43.511000','tweet sa userom','2022-07-29 15:25:43.511000',78),
                            (80,'2022-07-29 15:25:43.511000','tweet sa userom','2022-07-29 15:25:43.511000',78),
                            (81,'2022-07-29 15:25:43.511000','tweet sa userom','2022-07-29 15:25:43.511000',78),
                            (84,'2022-07-29 15:25:43.511000','tweet sa userom od Sandija','2022-07-29 15:25:43.511000',82),
                            (104,'2022-07-29 15:25:43.511000','tweet sa userom od Sandija num2','2022-07-29 15:25:43.511000',82);

INSERT INTO `comments` VALUES (85,84,'2022-07-29 12:25:43.511000','prvi komentar','2022-07-29 12:25:43.511000',82),
                              (86,84,'2022-07-29 12:25:43.511000','drugi komentar','2022-07-29 12:25:43.511000',82),
                              (87,84,'2022-07-29 12:25:43.511000','amarov komentar','2022-07-29 12:25:43.511000',78);

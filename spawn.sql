/*
Navicat PGSQL Data Transfer

Source Server         : localhost_5432
Source Server Version : 110500
Source Host           : localhost:5432
Source Database       : postgres
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 110500
File Encoding         : 65001

Date: 2019-10-25 18:43:04
*/


-- ----------------------------
-- Table structure for spawn
-- ----------------------------
DROP TABLE IF EXISTS "public"."spawn";
CREATE TABLE "public"."spawn" (
"id" int4 DEFAULT nextval('spawn_id_seq'::regclass) NOT NULL,
"actor_id" int4,
"location_x" int4,
"location_y" int4,
"location_z" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of spawn
-- ----------------------------
INSERT INTO "public"."spawn" VALUES ('1', '100', '5680', '6090', '-2000');
INSERT INTO "public"."spawn" VALUES ('2', '101', '2620', '3990', '2000');
INSERT INTO "public"."spawn" VALUES ('3', '102', '2820', '3990', '2000');
INSERT INTO "public"."spawn" VALUES ('4', '103', '40080', '43850', '1823');
INSERT INTO "public"."spawn" VALUES ('5', '104', '42370', '43640', '1598');
INSERT INTO "public"."spawn" VALUES ('6', '104', '42610', '44470', '1620');
INSERT INTO "public"."spawn" VALUES ('7', '104', '42910', '45230', '1665');
INSERT INTO "public"."spawn" VALUES ('8', '104', '42950', '43180', '1540');
INSERT INTO "public"."spawn" VALUES ('9', '104', '43240', '44200', '1622');
INSERT INTO "public"."spawn" VALUES ('10', '104', '43900', '44890', '1634');
INSERT INTO "public"."spawn" VALUES ('11', '104', '43940', '43640', '1563');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table spawn
-- ----------------------------
ALTER TABLE "public"."spawn" ADD PRIMARY KEY ("id");

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

Date: 2019-10-25 18:43:09
*/


-- ----------------------------
-- Table structure for character
-- ----------------------------
DROP TABLE IF EXISTS "public"."character";
CREATE TABLE "public"."character" (
"id" int4 DEFAULT nextval('character_id_seq'::regclass) NOT NULL,
"account_id" int4,
"class" varchar(255) COLLATE "default",
"experience" int8,
"level" int4,
"location_x" int4,
"location_y" int4,
"location_z" int4,
"name" varchar(255) COLLATE "default",
"race" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of character
-- ----------------------------
INSERT INTO "public"."character" VALUES ('1', '1', 'Fighter', '0', '1', '41271', '43727', '1662', 'PlayTest', 'Human');

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table character
-- ----------------------------
ALTER TABLE "public"."character" ADD PRIMARY KEY ("id");

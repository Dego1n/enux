/*
 Navicat Premium Data Transfer

 Source Server         : pg_local
 Source Server Type    : PostgreSQL
 Source Server Version : 100010
 Source Host           : localhost:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 100010
 File Encoding         : 65001

 Date: 25/10/2019 11:03:07
*/


-- ----------------------------
-- Table structure for spawn
-- ----------------------------
DROP TABLE IF EXISTS "public"."spawn";
CREATE TABLE "public"."spawn" (
  "id" int4 NOT NULL DEFAULT nextval('spawn_id_seq'::regclass),
  "actor_id" int4,
  "location_x" int4,
  "location_y" int4,
  "location_z" int4
)
;

-- ----------------------------
-- Records of spawn
-- ----------------------------
INSERT INTO "public"."spawn" VALUES (1, 100, 5680, 6090, -2000);
INSERT INTO "public"."spawn" VALUES (2, 101, 2620, 3990, 2000);
INSERT INTO "public"."spawn" VALUES (3, 102, 2820, 3990, 2000);
INSERT INTO "public"."spawn" VALUES (4, 103, 40080, 43850, 2020);
INSERT INTO "public"."spawn" VALUES (5, 104, 42370, 43640, 2000);
INSERT INTO "public"."spawn" VALUES (6, 104, 42610, 44470, 2000);
INSERT INTO "public"."spawn" VALUES (7, 104, 42910, 45230, 2000);
INSERT INTO "public"."spawn" VALUES (8, 104, 42950, 43180, 2000);
INSERT INTO "public"."spawn" VALUES (9, 104, 43240, 44200, 2000);
INSERT INTO "public"."spawn" VALUES (10, 104, 43900, 44890, 2000);
INSERT INTO "public"."spawn" VALUES (11, 104, 43940, 43640, 2000);

-- ----------------------------
-- Primary Key structure for table spawn
-- ----------------------------
ALTER TABLE "public"."spawn" ADD CONSTRAINT "spawn_pkey" PRIMARY KEY ("id");

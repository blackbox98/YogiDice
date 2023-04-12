# This is an auto-generated Django model module.
# You'll have to do the following manually to clean this up:
#   * Rearrange models' order
#   * Make sure each model has one field with primary_key=True
#   * Make sure each ForeignKey and OneToOneField has `on_delete` set to the desired behavior
#   * Remove `managed = False` lines if you wish to allow Django to create, modify, and delete the table
# Feel free to rename the models, but don't rename db_table values or field names.
from django.db import models


class BggData(models.Model):
    bgg_data_id = models.BigAutoField(primary_key=True)
    bgg_code = models.BigIntegerField()
    nick_name = models.CharField(max_length=50)
    rating_user = models.FloatField()

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'bgg_data'


class BoardGame(models.Model):
    game_id = models.BigAutoField(primary_key=True)
    age = models.IntegerField()
    bgg_code = models.BigIntegerField()
    contents = models.CharField(max_length=500, blank=True, null=True)
    contents_img_url = models.CharField(max_length=200, blank=True, null=True)
    difficulty = models.FloatField()
    max_players = models.IntegerField(blank=True, null=True)
    max_time = models.CharField(max_length=50, blank=True, null=True)
    min_players = models.IntegerField(blank=True, null=True)
    min_time = models.CharField(max_length=50, blank=True, null=True)
    playing_time = models.CharField(max_length=50, blank=True, null=True)
    publish_year = models.IntegerField()
    rating_bl = models.FloatField()
    rating_user = models.FloatField()
    thumb_url = models.CharField(max_length=200)
    title_eng = models.CharField(max_length=100)
    title_kr = models.CharField(max_length=100)
    youtube_url = models.CharField(max_length=200, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'board_game'


class BoardGameCafe(models.Model):
    cafe_id = models.BigAutoField(primary_key=True)
    address = models.CharField(max_length=200)
    name = models.CharField(max_length=100)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'board_game_cafe'


class Bookmark(models.Model):
    bookmark_id = models.BigAutoField(primary_key=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'bookmark'


class CafeGameList(models.Model):
    game_list_id = models.BigAutoField(primary_key=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    cafe = models.ForeignKey(BoardGameCafe, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'cafe_game_list'


class Category(models.Model):
    cate_id = models.BigAutoField(primary_key=True)
    name = models.CharField(max_length=50)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'category'


class CategoryGroup(models.Model):
    cate_group_id = models.BigAutoField(primary_key=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    cate = models.ForeignKey(Category, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'category_group'


class History(models.Model):
    history_id = models.BigAutoField(primary_key=True)
    created_date = models.DateTimeField(blank=True, null=True)
    rating = models.FloatField()
    review = models.CharField(max_length=1000, blank=True, null=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    user = models.ForeignKey('User', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'history'


class Mechanism(models.Model):
    mechanism_id = models.BigAutoField(primary_key=True)
    name = models.CharField(max_length=50)
    parents_mec = models.CharField(max_length=50)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'mechanism'


class MechanismGroup(models.Model):
    mechanism_group_id = models.BigAutoField(primary_key=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    mechanism = models.ForeignKey(Mechanism, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'mechanism_group'


class Type(models.Model):
    type_id = models.IntegerField(primary_key=True)
    name = models.CharField(max_length=50)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'type'


class TypeGroup(models.Model):
    type_group_id = models.BigAutoField(primary_key=True)
    game = models.ForeignKey(BoardGame, models.DO_NOTHING, blank=True, null=True)
    type_id = models.IntegerField(blank=True, null=True)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'type_group'


class User(models.Model):
    user_id = models.BigAutoField(primary_key=True)
    kakao_id = models.CharField(max_length=100)
    nick_name = models.CharField(max_length=50)
    reviewed = models.CharField(max_length=255)
    role = models.CharField(max_length=255)

    class Meta:
        managed = False
        app_label = "referencemysql"
        db_table = 'user'

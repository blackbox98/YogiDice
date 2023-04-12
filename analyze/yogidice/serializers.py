from dataclasses import field
from rest_framework import serializers
from .models import (
    BggData,
    BoardGame,
    BoardGameCafe,
    Bookmark,
    CafeGameList,
    Category,
    CategoryGroup,
    History,
    Mechanism,
    MechanismGroup,
    Type,
    TypeGroup,
    User
)

class BggDataSerializer(serializers.ModelSerializer):
    class Meta:
        model = BggData
        fields = '__all__'

class BoardGameSerializer(serializers.ModelSerializer):
    class Meta:
        model = BoardGame
        fields = '__all__'

class BoardGameCafeSerializer(serializers.ModelSerializer):
    class Meta:
        model = BoardGameCafe
        fields = '__all__'

class BookmarkSerializer(serializers.ModelSerializer):
    class Meta:
        model = Bookmark
        fields = '__all__'
    
class CafeGameListSerializer(serializers.ModelSerializer):
    class Meta:
        model = CafeGameList
        fields = '__all__'

class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = '__all__'

class CategoryGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = CategoryGroup
        fields = '__all__'

class HistorySerializer(serializers.ModelSerializer):
    class Meta:
        model = History
        fields = '__all__'

class MechanismSerializer(serializers.ModelSerializer):
    class Meta:
        model = Mechanism
        fields = '__all__'
    
class MechanismGroupListSerializer(serializers.ModelSerializer):
    class Meta:
        model = MechanismGroup
        fields = '__all__'

class TypeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Type
        fields = '__all__'

class TypeGroupSerializer(serializers.ModelSerializer):
    class Meta:
        model = TypeGroup
        fields = '__all__'

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

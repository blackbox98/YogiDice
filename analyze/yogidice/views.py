from urllib import response
import json

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
from .serializers import (
    BggDataSerializer,              ## BggData
    BoardGameSerializer,            ## BoardGame
    BoardGameCafeSerializer,        ## BoardGameCafe
    BookmarkSerializer,             ## Bookmark
    CafeGameListSerializer,         ## CafeGameList
    CategorySerializer,             ## Category
    CategoryGroupSerializer,        ## CategoryGroup
    HistorySerializer,              ## History
    MechanismSerializer,            ## Mechanism
    MechanismGroupListSerializer,   ## MechanismGroup
    TypeSerializer,                 ## Type
    TypeGroupSerializer,            ## TypeGroup
    UserSerializer,                 ## Type
) 
from rest_framework.decorators import api_view
from rest_framework.response import Response 
from rest_framework.status import (
    HTTP_201_CREATED,
    HTTP_204_NO_CONTENT,
)

from django.shortcuts import get_object_or_404
from django.http import ( HttpResponse, JsonResponse)
from . import CateModel
from . import YoDaModel

@api_view(['GET'])
def bggdata_list(request):
    if request.method == 'GET':
        name = BggData.objects.all()
        serializer = BggDataSerializer(name, many=True)
        return Response(serializer.data)


@api_view(['GET'])
def boardgame_list(request):
    if request.method == 'GET':
        name = BoardGame.objects.all()
        serializer = BoardGameSerializer(name, many=True)
        return Response(serializer.data)

@api_view(['GET'])
def recommend_detail(request, game_id):
    model_result = CateModel.calccate(game_id)
    main_dic = {}
    mec_cnt = 0
    for elem in model_result:
        mec_cnt += 1
        main_dic[mec_cnt] = elem
    return JsonResponse(main_dic)


@api_view(['GET'])
def get_user_data(request, user):
    if request.method == 'GET':
        data = History.objects.filter(user_id = user).order_by('-created_date')
        serializer = HistorySerializer(data, many=True)
        list = []
        score = []
        cnt = 0
        for i in serializer.data:
            if cnt == 3 : break
            if i['rating'] == 0:
                continue
            else :
                list.append(i['game'])
                score.append(i['rating'])
                cnt += 1
        model_result = {"game" : YoDaModel.search(list, score)}
        # model_result = json.dumps(model_result)
        return JsonResponse(model_result, safe=False)

@api_view(['GET'])
def game_detail(request, game_id):
    if request.method == 'GET':
        bg = BoardGame.objects.filter(bgg_code=game_id)
        serializer = BoardGameSerializer(bg)
        return Response(serializer.data)


        
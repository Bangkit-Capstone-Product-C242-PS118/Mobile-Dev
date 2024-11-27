from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from .models import Commodity, Province, PriceHistory, SavedItem
from .serializers import CommoditySerializer, ProvinceSerializer, PriceHistorySerializer, SavedItemSerializer

class CommodityViewSet(viewsets.ModelViewSet):
    queryset = Commodity.objects.all()
    serializer_class = CommoditySerializer

class ProvinceViewSet(viewsets.ModelViewSet):
    queryset = Province.objects.all()
    serializer_class = ProvinceSerializer

class PriceHistoryViewSet(viewsets.ModelViewSet):
    queryset = PriceHistory.objects.all()
    serializer_class = PriceHistorySerializer

class SavedItemViewSet(viewsets.ModelViewSet):
    queryset = SavedItem.objects.all()
    serializer_class = SavedItemSerializer

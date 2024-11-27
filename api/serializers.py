from rest_framework import serializers
from .models import Commodity, Province, PriceHistory, SavedItem

class CommoditySerializer(serializers.ModelSerializer):
    class Meta:
        model = Commodity
        fields = '__all__'

class ProvinceSerializer(serializers.ModelSerializer):
    class Meta:
        model = Province
        fields = '__all__'

class PriceHistorySerializer(serializers.ModelSerializer):
    class Meta:
        model = PriceHistory
        fields = '__all__'

class SavedItemSerializer(serializers.ModelSerializer):
    class Meta:
        model = SavedItem
        fields = '__all__'

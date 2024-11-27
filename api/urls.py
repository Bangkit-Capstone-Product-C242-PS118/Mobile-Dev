from rest_framework.routers import DefaultRouter
from .views import CommodityViewSet, ProvinceViewSet, PriceHistoryViewSet, SavedItemViewSet

router = DefaultRouter()
router.register(r'commodities', CommodityViewSet)
router.register(r'provinces', ProvinceViewSet)
router.register(r'price_history', PriceHistoryViewSet)
router.register(r'saved_items', SavedItemViewSet)

urlpatterns = router.urls

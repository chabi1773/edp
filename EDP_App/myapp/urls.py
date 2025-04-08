from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import UserProfileViewSet, TemperatureRecordViewSet

router = DefaultRouter()
router.register(r'users', UserProfileViewSet)
router.register(r'temperatures', TemperatureRecordViewSet)

urlpatterns = [
    path('', include(router.urls)),
]
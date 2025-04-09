from django.urls import path, include
#from rest_framework.routers import DefaultRouter
#from .views import UserProfileViewSet, TemperatureRecordViewSet
from . import views
'''
router = DefaultRouter()
router.register(r'users', UserProfileViewSet)
router.register(r'temperatures', TemperatureRecordViewSet)
'''

urlpatterns = [
    path('api/users', views.get_users),
    path('api/user/<int:user_id>', views.get_user_data)
]
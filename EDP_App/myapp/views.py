from django.shortcuts import render
from .models import UserProfile, TemperatureRecord
from rest_framework import viewsets
from .serializers import UserProfileSerializer, TemperatureRecordSerializer

class UserProfileViewSet(viewsets.ModelViewSet):
    queryset = UserProfile.objects.all()
    serializer_class = UserProfileSerializer

class TemperatureRecordViewSet(viewsets.ModelViewSet):
    queryset = TemperatureRecord.objects.all()
    serializer_class = TemperatureRecordSerializer


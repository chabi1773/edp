#from django.shortcuts import render
from .models import UserProfile, TemperatureRecord
#from rest_framework import viewsets
#from .serializers import UserProfileSerializer, TemperatureRecordSerializer
from rest_framework.decorators import api_view
from rest_framework.response import Response



@api_view(['GET'])
def get_users(request):
    users = UserProfile.objects.all()
    data = [{'id':u.user_id, 'name': u.user_name} for u in users]
    return Response(data)


@api_view(['GET'])
def get_user_data(request, user_id):
    try:
        user = UserProfile.objects.get(pk = user_id)
    except UserProfile.DoesNotExist:
        return Response({'error': 'User not found'}, status = 404)

    records = TemperatureRecord.objects.filter(user = user).order_by('-record_time')
    record_list = [
        {'temperature': r.temperature, 'record_time': r.record_time} for r in records
    ]
    current_temp = record_list[0] if record_list else None

    return Response({
        'user': user.user_name,
        'current_temp': current_temp,
        'records': record_list,

    })
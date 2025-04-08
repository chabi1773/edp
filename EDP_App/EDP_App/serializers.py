from rest_framework import serializers
from .models import UserProfile, TemperatureRecord

class TemperatureRecordSerializer(serializers.ModelSerializer):
    class Meta:
        model = TemperatureRecord
        fields = '__all__'
        
class UserProfileSerializer(serilazers.ModelSerializer):
    temperature_records = TemperatureRecordSerializer(many= True, read_only = True)
    class Meta:
        model = UserProfile
        fields = ["user_id","user_name","status","temperature_records"]
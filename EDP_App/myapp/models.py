from django.db import models
from django.contrib.auth.models import User

# Create your models here.
class UserProfile(models.Model):
    user_id = models.AutoField(primary_key = True)
    user_name = models.CharField(max_length = 100)
    status = models.BooleanField(default=False) #discharged?


class TemperatureRecord(models.Model):
    user = models.ForeignKey(UserProfile, on_delete = models.CASCADE)
    temperature = models.FloatField()
    record_time = models.DateTimeField(auto_now_add = True)


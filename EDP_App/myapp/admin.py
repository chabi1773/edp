from django.contrib import admin

from .models import UserProfile, TemperatureRecord

admin.site.register(UserProfile)
admin.site.register(TemperatureRecord)
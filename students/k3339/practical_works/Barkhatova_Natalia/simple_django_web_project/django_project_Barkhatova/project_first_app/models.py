from django.db import models


class Owner(models.Model):
    last_name = models.CharField(max_length=30, null=False)
    first_name = models.CharField(max_length=50, null=False)
    birth_date = models.DateTimeField(null=True)
    cars = models.ManyToManyField('Car', through='Owning', related_name='cars')


class Car(models.Model):
    license_plate = models.CharField(max_length=15, null=False)
    brand = models.CharField(max_length=20, null=False)
    model = models.CharField(max_length=20, null=False)
    color = models.CharField(max_length=30, null=True)
    owners = models.ManyToManyField('Owner', through='Owning', related_name='owners')


class Owning(models.Model):
    owner = models.ForeignKey(Owner, on_delete=models.CASCADE)
    car = models.ForeignKey(Car, on_delete=models.CASCADE)
    start_date = models.DateTimeField(null=False)
    end_date = models.DateTimeField(null=True)


class License(models.Model):
    owner = models.ForeignKey(Owner, on_delete=models.CASCADE)
    number = models.CharField(max_length=10, null=False)
    type = models.CharField(max_length=10, null=False)
    date_of_issue = models.DateTimeField(null=True)

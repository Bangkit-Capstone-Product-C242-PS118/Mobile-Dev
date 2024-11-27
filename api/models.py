from django.db import models

class Commodity(models.Model):
    name = models.CharField(max_length=100)
    image = models.ImageField(upload_to='commodities/')
    description = models.TextField()

class Province(models.Model):
    name = models.CharField(max_length=100)
    image = models.ImageField(upload_to='provinces/')

class PriceHistory(models.Model):
    commodity = models.ForeignKey(Commodity, on_delete=models.CASCADE)
    province = models.ForeignKey(Province, on_delete=models.CASCADE)
    date = models.DateField()
    price = models.FloatField()

class SavedItem(models.Model):
    commodity = models.ForeignKey(Commodity, on_delete=models.CASCADE)
    province = models.ForeignKey(Province, on_delete=models.CASCADE)
    time_saved = models.DateTimeField(auto_now_add=True)

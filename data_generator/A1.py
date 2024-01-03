from faker import Faker
import random
import json

words = [
    "car", "plane", "motorcycle", "ship", "sailing", "basketball", "football", "volleyball", "running", "skiing", "snowboarding",
    "tennis", "bicycle", "swimming", "climbing", "trekking", "chess", "music", "book", "writing", "drawing",
    "photography", "design", "blog", "engine", "guitar", "piano", "violin", "drums", "camping", "dancing", "language",
    "coding", "swift", "java", "python", "playstation", "counter-strike", "fortnite", "dota", "gta", "computer",
    "yoga", "fencing", "theater", "cinema", "garden", "tree", "plant", "bmw", "mercedes", "honda", "suzuki", "audi",
    "citroen", "peugeot", "ford", "bentley", "dacia", "yamaha", "ibanez", "gibson", "jackson", "rock", "pop", 
    "hiphop", "classical", "rap", "lego", "puzzle", "model", "painting", "picture", "fish", "food", "chef", "turkish", 
    "german", "english", "american", "dutch", "french", "italian", "turkey", "syria", "rio", "sweden", "netherlands", 
    "dubai", "qatar", "belgium", "diy", "macos", "windows", "ios", "iphone", "ipad", "ipod", "airpod", "samsung", 
    "htc", "huawei", "lg", "philips", "sony", "dyson", "grundig", "ball", "chair", "armchair", "sewing", "knitting", 
    "hashmap", "graph", "linkedlist"
]

hashtags = [
    "#car", "#cardio", "#cars", "#cartoon", "#carsofinstagram", "#CarsWithOutLimits", "#Caracas", 
    "#caribbean", "#carlifestyle", "#cargram", "#career", "#care", "#carinstagram", "#carbs", "#cartier", "#carspotting",
    "#foodphoto", "#foodstyling", "#foodshare", "#foodlove", "#foodiegram", "#foodoftheday", "#foodforthought", 
    "#foodisfuel", "#foodcoma", "#foodaddict", "#foodtruck", "#foodnetwork", "#travelpic", 
    "#travelers", "#travelstoke", "#travelgirl", "#traveldiary", "#traveldeeper", "#travellingthroughtheworld",
    "#travelmore", "#coderlife", "#codes", "#codelife", "#codeart", "#moneytips", "#moneybox", "#moneyman" 
]

a = []

fake = Faker()
fake.unique.clear()

followerCount = 0
followingCount = 0

def generate_user():
    global followerCount 
    followerCount = random.randint(50, 90)

    global followingCount 
    followingCount = random.randint(50, 90)

    s = fake.unique.user_name()
    a.append(s)

    return {
        "kullanici_adi": s,
        "ad_soyad": fake.unique.name(),
        "takipci_sayisi": followerCount,
        "takip_edilen_sayisi": followingCount,
        "dil": fake.language_code(),
        "bolge": fake.country_code(representation="alpha-2")
    }

def generate_tweet():

    sentence = ' '.join(random.choice(words) for _ in range(6))

    return {
        "tweet_id": fake.unique.random_int(min=0, max=41111000),
        "icerik": sentence,
        "hashtag": random.choice(hashtags)
    }

fake_users_data = []
for _ in range(30000):
    user_data = {
        "kullanici_bilgileri": generate_user(),
        "tweet_icerikleri": [generate_tweet() for _ in range(50)]
    }
    fake_users_data.append(user_data)

with open('users.json', 'w') as file:
    json.dump(fake_users_data, file, indent=2)

for i in a:
    print(i)


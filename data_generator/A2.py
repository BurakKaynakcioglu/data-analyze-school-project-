import json
import random

def generate_user(kullanici_bilgileri):
    return {
        "kullanici_adi": kullanici_bilgileri['kullanici_adi'],
        "ad_soyad": kullanici_bilgileri['ad_soyad'],
        "takipci_sayisi": kullanici_bilgileri['takipci_sayisi'],
        "takip_edilen_sayisi": kullanici_bilgileri['takip_edilen_sayisi'],
        "dil": kullanici_bilgileri['dil'],
        "bolge": kullanici_bilgileri['bolge']
    }

def generate_tweet(tweet):
    return {
        "tweet_id": tweet['tweet_id'],
        "icerik": tweet['icerik'],
        "hashtag": tweet['hashtag']
    }

def generate_followers_following(kullanici_bilgileri):
    takipciSayisi = kullanici_bilgileri['takipci_sayisi']
    takipEdilenSayisi = kullanici_bilgileri['takip_edilen_sayisi']

    takipciler = []
    while True:
        takipciler = random.sample(usernames, takipciSayisi)
        
        if kullanici_bilgileri['kullanici_adi'] not in takipciler:
            break

    takipEdilenler = []
    while True:
        takipEdilenler = random.sample(usernames, takipEdilenSayisi)
        
        if kullanici_bilgileri['kullanici_adi'] not in takipEdilenler:
            break


    followers = [takipci for takipci in takipciler]
    following = [takipEdilen for takipEdilen in takipEdilenler]

    return {
        "takip_edilenler": followers,
        "takipciler": following
    }


#################################################################

with open('users.json', 'r', encoding='utf-8') as dosya:
    veri = json.load(dosya)

usernames = []
for kullanici in veri:
    usernames.append(kullanici['kullanici_bilgileri']['kullanici_adi'])

fake_users_data = []

for kullanici_verisi in veri:
    user_data = {
        "kullanici_bilgileri": generate_user(kullanici_verisi['kullanici_bilgileri']),
        "tweet_icerikleri": [generate_tweet(tweet) for tweet in kullanici_verisi['tweet_icerikleri']],
        "takip_edilen_ve_takipciler": generate_followers_following(kullanici_verisi['kullanici_bilgileri'])
    }
    fake_users_data.append(user_data)

with open('fake_users_data.json', 'w') as file:
    json.dump(fake_users_data, file, indent=2)

print(len(veri))

for kullanici in usernames:
    print(kullanici)
import requests
import os
import os.path

api_url = 'https://api.tinypng.com/shrink'
key = 'K3dnMHzqEb0ep0fuXKd-nbSL86NivVAc'
input = 'a.png'
output = 'a.tiny.png'

proxies = {
	'http': 'http://web-proxy.oa.com:8080',
	'https': 'http://web-proxy.oa.com:8080',
}
auth = (
	'user',
	key,
)

def tiny(input, output):
    if os.path.exists(input + '.tiny'):
        print '%s has been tinied!' % input
        return
    print 'tinying %s' % input
    with file(input, 'rb') as f:
        r = requests.post(
            api_url,
            proxies=proxies, 
            auth=auth,
            data=f)
    # print r.content
    if r.status_code == 201:
        tmp_url = r.headers['Location']
        # print tmp_url
        r2 = requests.get(
                tmp_url,
                proxies=proxies,
            )

        # print r2.content
        with file(output, 'wb') as f:
            f.write(r2.content)
    os.rename(input, input + '.tiny')
    os.rename(output, input)
    # raw_input()

if __name__ == '__main__':
	#tiny(input, output)
	for item in os.listdir('.'):
		if item.endswith('png') or item.endswith('PNG'):
			tiny(item, 'a.tiny.png')

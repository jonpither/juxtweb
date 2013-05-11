go:	up
	lein up

up:
	cd ../up; \
	lein sub clean; \
	lein sub install

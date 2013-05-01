go:	up
	lein up

up:
	cd ../up; \
	LEIN_FAST_TRAMPOLINE= lein sub clean; \
	LEIN_FAST_TRAMPOLINE= lein sub install

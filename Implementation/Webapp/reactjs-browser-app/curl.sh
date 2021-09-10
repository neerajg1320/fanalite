# Success
echo "Case 1)"
echo "Req:"
echo "userName: alice"
echo "Res:"
curl -X POST -H 'Content-Type: application/json' -d '{"userName": "alice"}' 'http://localhost:8080/login'
echo ""

# Failure
echo ""
echo "Case 2)"
echo "Req:"
echo "Username: bob"
echo "Res:"
curl -X POST -H 'Content-Type: application/json' -d '{"userName": "bob"}' 'http://localhost:8080/login'
echo ""

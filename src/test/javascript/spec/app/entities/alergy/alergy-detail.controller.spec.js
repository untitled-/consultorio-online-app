'use strict';

describe('Controller Tests', function() {

    describe('Alergy Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockAlergy, MockPathologicBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockAlergy = jasmine.createSpy('MockAlergy');
            MockPathologicBkg = jasmine.createSpy('MockPathologicBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Alergy': MockAlergy,
                'PathologicBkg': MockPathologicBkg
            };
            createController = function() {
                $injector.get('$controller')("AlergyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineAppApp:alergyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

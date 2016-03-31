'use strict';

describe('Controller Tests', function() {

    describe('Trauma Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTrauma, MockPathologicBkg;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTrauma = jasmine.createSpy('MockTrauma');
            MockPathologicBkg = jasmine.createSpy('MockPathologicBkg');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Trauma': MockTrauma,
                'PathologicBkg': MockPathologicBkg
            };
            createController = function() {
                $injector.get('$controller')("TraumaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineAppApp:traumaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

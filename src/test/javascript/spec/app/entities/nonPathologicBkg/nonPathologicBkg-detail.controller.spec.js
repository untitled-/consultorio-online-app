'use strict';

describe('Controller Tests', function() {

    describe('NonPathologicBkg Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNonPathologicBkg, MockImmunization, MockPatient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNonPathologicBkg = jasmine.createSpy('MockNonPathologicBkg');
            MockImmunization = jasmine.createSpy('MockImmunization');
            MockPatient = jasmine.createSpy('MockPatient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'NonPathologicBkg': MockNonPathologicBkg,
                'Immunization': MockImmunization,
                'Patient': MockPatient
            };
            createController = function() {
                $injector.get('$controller')("NonPathologicBkgDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'consultorioOnlineUiApp:nonPathologicBkgUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
